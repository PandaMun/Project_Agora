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

package com.ssafy.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ssafy.application.room.RoomManager;
import com.ssafy.application.user.UserRegistry;
import com.ssafy.domain.Room;
import com.ssafy.domain.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kurento.client.IceCandidate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

/**
 * @author Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
@Slf4j
@RequiredArgsConstructor
public class CallHandler extends TextWebSocketHandler {

    private static final Gson gson = new GsonBuilder().create();

    private final RoomManager roomManager;

    private final UserRegistry registry;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        final JsonObject jsonMessage = gson.fromJson(message.getPayload(), JsonObject.class);
        final UserSession user = registry.getBySession(session);

        if (user != null) {
            log.debug("Incoming message from user '{}': {}", user.getName(), jsonMessage);
        } else {
            log.debug("Incoming message from new user: {}", jsonMessage);
        }

        switch (jsonMessage.get("id").getAsString()) {
            case "joinRoom":
                joinRoom(jsonMessage, session);
                break;
            case "receiveVideoFrom":
                final String senderName = jsonMessage.get("sender").getAsString();
                final UserSession sender = registry.getByName(senderName);
                final String sdpOffer = jsonMessage.get("sdpOffer").getAsString();
                user.receiveVideoFrom(sender, sdpOffer);
                break;
            case "leaveRoom":
                leaveRoom(user);
                break;
            case "onIceCandidate":
                JsonObject candidate = jsonMessage.get("candidate").getAsJsonObject();

                if (user != null) {
                    IceCandidate cand = new IceCandidate(candidate.get("candidate").getAsString(),
                            candidate.get("sdpMid").getAsString(), candidate.get("sdpMLineIndex").getAsInt());
                    user.addCandidate(cand, jsonMessage.get("name").getAsString());
                }
                break;
            case "startSpeaking":
                String debateId = jsonMessage.get("debateId").getAsString();
                Room room = roomManager.getRoom(debateId);
                room.startCountDown(user);
                break;
            case "pauseSpeaking":
                debateId = jsonMessage.get("debateId").getAsString();
                room = roomManager.getRoom(debateId);
                room.pauseCountDown(user);
                break;
            case "shareScreen":
                shareScreen(jsonMessage, session);
                break;
            default:
                break;
        }
    }

    private void shareScreen(JsonObject params, WebSocketSession session) throws IOException {
        final String debateId = params.get("debateId").getAsString();
        final String userName = "screen_" + params.get("userName").getAsString();
        final String roomName = params.get("roomName").getAsString();
        final String position = params.get("position").getAsString();

        log.info("PARTICIPANT {}: trying to join room {}", userName, roomName);

        Room room = roomManager.getRoom(debateId, roomName);
        final UserSession user = room.join(userName, position, session);
        registry.register(user);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        UserSession user = registry.removeBySession(session);
        roomManager.getRoom(user.getRoomName()).leave(user);
    }

    private void joinRoom(JsonObject params, WebSocketSession session) throws IOException {
        final String debateId = params.get("debateId").getAsString();
        final String userName = params.get("userName").getAsString();
        final String roomName = params.get("roomName").getAsString();
        final String position = params.get("position").getAsString();

        log.info("PARTICIPANT {}: trying to join room {}", userName, debateId);

        Room room = roomManager.getRoom(debateId, roomName);
        final UserSession user = room.join(userName, position, session);
        registry.register(user);
    }

    private void leaveRoom(UserSession user) throws IOException {
        final Room room = roomManager.getRoom(user.getRoomName());
        room.leave(user);

        if (room.getParticipants().isEmpty()) {
            roomManager.removeRoom(room);
        }
    }
}
