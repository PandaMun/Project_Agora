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

package com.ssafy.application.user;

import com.ssafy.domain.UserSession;
import com.ssafy.repository.UserStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Map of users registered in the system. This class has a concurrent hash map to store users, using
 * its name as key in the map.
 *
 * @author Boni Garcia (bgarcia@gsyc.es)
 * @author Micael Gallego (micael.gallego@gmail.com)
 * @authos Ivan Gracia (izanmail@gmail.com)
 * @since 4.3.1
 */
@RequiredArgsConstructor
public class UserRegistry {

    private final UserStorage userStorage;

    public void register(UserSession user) {
        userStorage.putByName(user.getName(), user);
        userStorage.putBySessionId(user.getSession().getId(), user);
    }

    public UserSession getByName(String name) {
        return userStorage.getByName(name);
    }

    public UserSession getBySession(WebSocketSession session) {
        return userStorage.getBySessionId(session.getId());
    }

    public boolean exists(String name) {
        return userStorage.containByName(name);
    }

    public UserSession removeBySession(WebSocketSession session) {
        final UserSession user = getBySession(session);
        userStorage.removeByName(user.getName());
        userStorage.removeBySessionId(session.getId());
        return user;
    }

}
