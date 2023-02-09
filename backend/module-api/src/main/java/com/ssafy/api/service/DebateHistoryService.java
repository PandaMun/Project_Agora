package com.ssafy.api.service;

import com.ssafy.entity.rdbms.Action;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.DebateHistory;
import com.ssafy.entity.rdbms.User;

public interface DebateHistoryService {
    DebateHistory createDebateHistory(Action action, Debate debate, User user);
}
