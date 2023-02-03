package com.ssafy.domain;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.kurento.client.Continuation;
import org.kurento.client.MediaPipeline;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Getter
public class TotalTimeLimitRoom implements Closeable, Room {
    private final String roomName;
    private final String debateId;
    private final MediaPipeline pipeline;
    private final ConcurrentMap<String, Participant> participants;
    private final ConcurrentMap<String, Position> positions;
    private Timer timer;

    public TotalTimeLimitRoom(String roomName, String debateId, MediaPipeline pipeline, long totalTime, String... positionNames) {
        this.roomName = roomName;
        this.debateId = debateId;
        this.pipeline = pipeline;

        this.participants = new ConcurrentHashMap<>();
        this.positions = new ConcurrentHashMap<>();

        if (positionNames == null) {
            positionNames = new String[] {"찬성", "반대"};
        }

        Arrays.stream(positionNames).forEach(position -> positions.put(position, new Position(position, totalTime)));
    }


    @PreDestroy
    private void shutdown() {
        this.close();
    }

    public Participant join(String userName, String positionName, WebSocketSession session, boolean isScreen) throws IOException {
        log.info("ROOM {}: adding participant {}", this.roomName, userName);
        final Participant participant = new Participant(userName, this.roomName, positions.get(positionName), session, this.pipeline);
        if (isScreen) {
            participant.turnOnScreen();
        }
        joinRoom(participant);
        participants.put(participant.getName(), participant);
        sendParticipantNames(participant);
        return participant;
    }

    public void leave(Participant user) throws IOException {
        log.debug("PARTICIPANT {}: Leaving room {}", user.getName(), this.roomName);
        this.removeParticipant(user.getName());
        user.close();
    }

    private Collection<String> joinRoom(Participant newParticipant) {
        final JsonObject newParticipantMsg = new JsonObject();
        newParticipantMsg.addProperty("id", "newParticipantArrived");
        newParticipantMsg.addProperty("name", newParticipant.getName());
        newParticipantMsg.addProperty("position", newParticipant.getPosition().getPositionName());
        newParticipantMsg.addProperty("isScreen", newParticipant.isScreen());

        final List<String> participantsList = new ArrayList<>(participants.values().size());
        log.info("ROOM {}: notifying other participants of new participant {}", roomName, newParticipant.getName());

        for (final Participant participant : participants.values()) {
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
        for (final Participant participant : participants.values()) {
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

    public void sendParticipantNames(Participant user) throws IOException {

        final JsonArray participantsArray = new JsonArray();
        for (final Participant participant : this.getParticipants()) {
            if (!participant.equals(user)) {
                final JsonObject participantInfo = new JsonObject();
                participantInfo.addProperty("name", participant.getName());
                participantInfo.addProperty("position", participant.getPosition().getPositionName());
                participantInfo.addProperty("isScreen", participant.isScreen());

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

    public Collection<Participant> getParticipants() {
        return participants.values();
    }

    public Participant getParticipant(String name) {
        return participants.get(name);
    }

    @Override
    public void close() {
        for (final Participant user : participants.values()) {
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
                log.trace("ROOM {}: Released Pipeline", TotalTimeLimitRoom.this.roomName);
            }

            @Override
            public void onError(Throwable cause) throws Exception {
                log.warn("PARTICIPANT {}: Could not release Pipeline", TotalTimeLimitRoom.this.roomName);
            }
        });

        log.debug("Room {} closed", this.roomName);
    }

    @Override
    public void startCountDown(Participant user) {
        String positionName = user.getPositionName();

        TimerTask task = new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                long time = positions.get(positionName).getLastSeconds();
                if (time > 0) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", "timeRemaining");
                    jsonObject.addProperty("position", positionName);
                    jsonObject.addProperty("time", time--);
                    for (final Participant participant : participants.values()) {
                        try {
                            participant.sendMessage(jsonObject);
                        } catch (final IOException e) {
                            log.debug("ROOM {}: participant {} could not be notified", user.getName(), participant.getName(), e);
                        }
                    }
                } else {
                    positions.get(positionName).updateLastSecond(time);
                    pauseCountDown(user);
                }
            }
        };
        this.timer = new Timer();
        this.timer.schedule(task, 1000, 1000);
    }

    @Override
    public void pauseCountDown(Participant user) {
        String positionName = getRoomName();
        this.timer.cancel();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", "pauseSpeaking");
        jsonObject.addProperty("time", positions.get(positionName).getLastSeconds());

        for (final Participant participant : participants.values()) {
            try {
                participant.sendMessage(jsonObject);
            } catch (final IOException e) {
                log.debug("ROOM {}: participant {} could not be notified", user.getName(), participant.getName(), e);
            }
        }
    }

    public void audioOn(Participant user) {

    }

    public void audioOff() {

    }
}
