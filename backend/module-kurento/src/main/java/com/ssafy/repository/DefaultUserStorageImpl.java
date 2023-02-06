package com.ssafy.repository;

import com.ssafy.domain.UserSession;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultUserStorageImpl implements UserStorage {

    private final Map<String, UserSession> usersByName = new ConcurrentHashMap<>();
    private final Map<String, UserSession> usersBySessionId = new ConcurrentHashMap<>();

    @Override
    public void putByName(String name, UserSession user) {
        this.usersByName.put(name, user);
    }

    @Override
    public void putBySessionId(String id, UserSession user) {
        this.usersBySessionId.put(id, user);
    }

    @Override
    public UserSession getByName(String name) {
        return this.usersByName.get(name);
    }

    @Override
    public UserSession getBySessionId(String id) {
        return this.usersBySessionId.get(id);
    }

    @Override
    public boolean containByName(String name) {
        return this.usersByName.containsKey(name);
    }

    @Override
    public boolean containBySessionId(String id) {
        return this.usersBySessionId.containsKey(id);
    }

    @Override
    public void removeByName(String name) {
        this.usersByName.remove(name);
    }

    @Override
    public void removeBySessionId(String id) {
        this.usersBySessionId.remove(id);
    }
}
