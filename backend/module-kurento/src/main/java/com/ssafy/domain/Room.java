/*
 * (C) Copyright 2014 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ssafy.domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.SneakyThrows;
import org.kurento.client.Continuation;
import org.kurento.client.MediaPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
@Getter
public class Room implements Closeable {
  private final Logger log = LoggerFactory.getLogger(Room.class);

  private final ConcurrentMap<String, UserSession> participants = new ConcurrentHashMap<>();
  private final MediaPipeline pipeline;
  private final String roomName;

  private final String debateId;

  private long time = 10 * 60;
  private Timer timer;

  public Room(String roomName, String debateId, MediaPipeline pipeline) {
    this.roomName = roomName;
    this.debateId = debateId;
    this.pipeline = pipeline;
    log.info("ROOM {} has been created", roomName);
  }

  @PreDestroy
  private void shutdown() {
    this.close();
  }

  public UserSession join(String userName, String position, WebSocketSession session) throws IOException {
    log.info("ROOM {}: adding participant {}", this.roomName, userName);
    final UserSession participant = new UserSession(userName, this.roomName, position, session, this.pipeline);
    joinRoom(participant);
    participants.put(participant.getName(), participant);
    sendParticipantNames(participant);
    return participant;
  }

  public void leave(UserSession user) throws IOException {
    log.debug("PARTICIPANT {}: Leaving room {}", user.getName(), this.roomName);
    this.removeParticipant(user.getName());
    user.close();
  }

  private Collection<String> joinRoom(UserSession newParticipant) throws IOException {
    final JsonObject newParticipantMsg = new JsonObject();
    newParticipantMsg.addProperty("id", "newParticipantArrived");
    newParticipantMsg.addProperty("name", newParticipant.getName());
    newParticipantMsg.addProperty("position", newParticipant.getPosition());

    final List<String> participantsList = new ArrayList<>(participants.values().size());
    log.info("ROOM {}: notifying other participants of new participant {}", roomName, newParticipant.getName());

    for (final UserSession participant : participants.values()) {
      if (newParticipant.getName().startsWith("screen_") || participant.getName().startsWith("screen_")) {
        continue;
      }

      try {
        participant.sendMessage(newParticipantMsg);
      } catch (final IOException e) {
        log.debug("ROOM {}: participant {} could not be notified", roomName, participant.getName(), e);
      }
      participantsList.add(participant.getName());
    }

    return participantsList;
  }

  private void removeParticipant(String name) throws IOException {
    participants.remove(name);

    log.debug("ROOM {}: notifying all users that {} is leaving the room", this.roomName, name);

    final List<String> unnotifiedParticipants = new ArrayList<>();
    final JsonObject participantLeftJson = new JsonObject();
    participantLeftJson.addProperty("id", "participantLeft");
    participantLeftJson.addProperty("name", name);
    for (final UserSession participant : participants.values()) {
      try {
        participant.cancelVideoFrom(name);
        participant.sendMessage(participantLeftJson);
      } catch (final IOException e) {
        unnotifiedParticipants.add(participant.getName());
      }
    }

    if (!unnotifiedParticipants.isEmpty()) {
      log.debug("ROOM {}: The users {} could not be notified that {} left the room", this.roomName,
          unnotifiedParticipants, name);
    }

  }

  public void sendParticipantNames(UserSession user) throws IOException {

    final JsonArray participantsArray = new JsonArray();
    for (final UserSession participant : this.getParticipants()) {
      if (!participant.equals(user)) {
        final JsonObject participantInfo = new JsonObject();
        participantInfo.addProperty("name", participant.getName());
        participantInfo.addProperty("position", participant.getPosition());

        participantsArray.add(participantInfo);
      }
    }

    final JsonObject existingParticipantsMsg = new JsonObject();
    existingParticipantsMsg.addProperty("id", "existingParticipants");
    existingParticipantsMsg.add("data", participantsArray);
    log.debug("PARTICIPANT {}: sending a list of {} participants", user.getName(),
        participantsArray.size());
    user.sendMessage(existingParticipantsMsg);
  }

  public Collection<UserSession> getParticipants() {
    return participants.values();
  }

  public UserSession getParticipant(String name) {
    return participants.get(name);
  }

  @Override
  public void close() {
    for (final UserSession user : participants.values()) {
      try {
        user.close();
      } catch (IOException e) {
        log.debug("ROOM {}: Could not invoke close on participant {}", this.roomName, user.getName(),
            e);
      }
    }

    participants.clear();

    pipeline.release(new Continuation<Void>() {

      @Override
      public void onSuccess(Void result) throws Exception {
        log.trace("ROOM {}: Released Pipeline", Room.this.roomName);
      }

      @Override
      public void onError(Throwable cause) throws Exception {
        log.warn("PARTICIPANT {}: Could not release Pipeline", Room.this.roomName);
      }
    });

    log.debug("Room {} closed", this.roomName);
  }

  public void startCountDown(UserSession user) {
    TimerTask task = new TimerTask() {
      @SneakyThrows
      @Override
      public void run() {
        if (time > 0) {
          JsonObject jsonObject = new JsonObject();
          jsonObject.addProperty("id", "timeRemaining");
          jsonObject.addProperty("time", time--);
          for (final UserSession participant : participants.values()) {
            try {
              participant.sendMessage(jsonObject);
            } catch (final IOException e) {
              log.debug("ROOM {}: participant {} could not be notified", user.getName(), participant.getName(), e);
            }
          }
        } else {
          timer.cancel();
        }
      }
    };
    this.timer = new Timer();
    this.timer.schedule(task, 1000, 1000);
  }

  public void pauseCountDown(UserSession user) throws IOException {
    this.timer.cancel();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("id", "pauseSpeaking");
    jsonObject.addProperty("time", time);
    for (final UserSession participant : participants.values()) {
      try {
        participant.sendMessage(jsonObject);
      } catch (final IOException e) {
        log.debug("ROOM {}: participant {} could not be notified", user.getName(), participant.getName(), e);
      }
    }
  }
}
