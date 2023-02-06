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

package com.ssafy.application.room;

import com.ssafy.domain.Room;
import com.ssafy.repository.RoomStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kurento.client.KurentoClient;

@Slf4j
@RequiredArgsConstructor
public class RoomManager {

    private final KurentoClient kurento;

    private final RoomStorage rooms;

    public Room getRoom(String debateId) {
        log.debug("Searching for room {}", debateId);
        Room room = rooms.get(debateId);

        if (room == null) {
            log.debug("Room {} not existent. Will create now!", debateId);
            room = new Room(room.getRoomName(), debateId, kurento.createMediaPipeline());
            rooms.add(debateId, room);
        }

        log.debug("Room {} found!", debateId);
        return room;
    }

    public Room getRoom(String debateId, String roomName) {
        log.debug("Searching for room {}", debateId);
        Room room = rooms.get(debateId);

        if (room == null) {
            log.debug("Room {} not existent. Will create now!", debateId);
            room = new Room(roomName, debateId, kurento.createMediaPipeline());
            rooms.add(debateId, room);
        }

        log.debug("Room {} found!", debateId);
        return room;
    }

    public void removeRoom(Room room) {
        this.rooms.remove(room.getRoomName());
        room.close();
        log.info("Room {} removed and closed", room.getRoomName());
    }

}
