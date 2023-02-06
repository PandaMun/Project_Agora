package com.ssafy.repository;

import com.ssafy.domain.Room;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultRoomStorageImpl extends ConcurrentHashMap<String, Room> implements RoomStorage {

    @Override
    public Room get(String roomName) {
        return super.get(roomName);
    }

    @Override
    public void add(String roomName, Room room) {
        this.put(roomName, room);
    }

    @Override
    public void remove(String name) {
        super.remove(name);
    }
}
