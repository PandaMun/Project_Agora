package com.ssafy.repository;

import com.ssafy.domain.Participant;

public interface UserStorage {
    void putByName(String name, Participant user);

    void putBySessionId(String id, Participant user);

    Participant getByName(String name);

    Participant getBySessionId(String id);

    boolean containByName(String name);

    boolean containBySessionId(String name);

    void removeByName(String name);

    void removeBySessionId(String id);
}
