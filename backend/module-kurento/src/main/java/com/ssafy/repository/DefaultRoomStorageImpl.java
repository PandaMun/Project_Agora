package com.ssafy.repository;

import com.ssafy.domain.Room;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultRoomStorageImpl extends ConcurrentHashMap<String, Room> implements RoomStorage {

    @Override
    public Room get(String debateId) {
        return super.get(debateId);
    }

    @Override
    public void add(String debateId, Room room) {
        this.put(debateId, room);
    }

    @Override
    public void remove(String debateId) {
        super.remove(debateId);
    }
}
