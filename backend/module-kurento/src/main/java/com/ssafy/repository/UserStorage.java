package com.ssafy.repository;

import com.ssafy.domain.UserSession;

public interface UserStorage {
    void putByName(String name, UserSession user);

    void putBySessionId(String id, UserSession user);

    UserSession getByName(String name);

    UserSession getBySessionId(String id);

    boolean containByName(String name);

    boolean containBySessionId(String name);

    void removeByName(String name);

    void removeBySessionId(String id);
}
