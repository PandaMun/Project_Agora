package com.ssafy.api.service;

import com.ssafy.entity.rdbms.Action;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.DebateHistory;
import com.ssafy.entity.rdbms.User;
import com.ssafy.repository.DebateHistoryRepository;
import com.ssafy.repository.DebateRepository;
import com.ssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("debateHistoryService")
@RequiredArgsConstructor
public class DebateHistoryServiceImpl implements DebateHistoryService{

    private final UserRepository userRepository;

    private final DebateRepository debateRepository;

    private final DebateHistoryRepository debateHistoryRepository;

    @Override
    @Transactional
    public DebateHistory createDebateHistory(Action action, Debate debate, User user) {
        DebateHistory debateHistory = new DebateHistory();
        debateHistory.setDebate(debate);
        debateHistory.setUser(user);
        debateHistory.setAction(action);
        return debateHistoryRepository.save(debateHistory);
    }
}
