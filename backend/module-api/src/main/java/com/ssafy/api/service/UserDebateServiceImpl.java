package com.ssafy.api.service;

import com.google.protobuf.Enum;
import com.ssafy.api.request.UserDebateRegisterPostReq;
import com.ssafy.entity.rdbms.Action;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.User;
import com.ssafy.entity.rdbms.UserDebate;
import com.ssafy.repository.DebateRepository;
import com.ssafy.repository.UserDebateRepository;
import com.ssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userDebateService")
@RequiredArgsConstructor
public class UserDebateServiceImpl implements UserDebateService{
    private final UserRepository userRepository;

    private final DebateRepository debateRepository;

    private final DebateHistoryService debateHistoryService;

    private final UserDebateRepository userDebateRepository;

    @Override
    @Transactional
    public UserDebate createUserDebate(UserDebateRegisterPostReq userDebateReq) {
        Debate debate  = debateRepository.findById(userDebateReq.getDebateId()).orElseThrow(NoClassDefFoundError::new);
        User user = userRepository.findByUserEmail(userDebateReq.getUserEmail()).orElseThrow(NoSuchFieldError::new);
        UserDebate userDebate = makeUserDebate(userDebateReq, user, debate);
        UserDebate savedUserDebate = userDebateRepository.save(userDebate);
        Action action = Action.valueOf("Join");
        debateHistoryService.createDebateHistory(action, debate, user);

        return userDebateRepository.save(userDebate);
    }

    @Override
    public void modifyUserDebateRole(String role) {
//        UserDebate userDebate = debateRepository.findById()
    }


    private UserDebate makeUserDebate(UserDebateRegisterPostReq userDebateReq, User user, Debate debate) {
        UserDebate userDebate = new UserDebate();
        userDebate.setDebate(debate);
        userDebate.setUser(user);
        userDebate.setRole(userDebateReq.getRole());
        return userDebate;
    }
}
