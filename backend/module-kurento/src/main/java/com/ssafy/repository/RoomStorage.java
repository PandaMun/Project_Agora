package com.ssafy.repository;

import com.ssafy.domain.Room;
import org.springframework.stereotype.Repository;

public interface RoomStorage {

    Room get(String roomName);

    void add(String roomName, Room room);

    void remove(String name);
}
