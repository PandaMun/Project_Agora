package com.ssafy.domain;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collection;

public interface Room {
    String getTitle();

    void allowSpeaking(Participant user);

    void pauseSpeaking(Participant user);

    Collection<Participant> getParticipants();

    void leave(Participant user) throws IOException;

    void close();

    Participant join(String userName, String position, WebSocketSession session, boolean isScreen) throws IOException;

    void sendComment(Participant user, String comment);

    String getDebateId();

    void terminateDebate(Participant user);
}
