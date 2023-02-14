package com.ssafy.api.service;

import com.ssafy.api.request.UserDebateRegisterPostReq;
import com.ssafy.api.response.DebateRes;
import com.ssafy.api.response.UserDebateHistory;
import com.ssafy.entity.rdbms.Action;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.User;
import com.ssafy.entity.rdbms.UserDebate;
import com.ssafy.repository.DebateRepository;
import com.ssafy.repository.UserDebateRepository;
import com.ssafy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("userDebateService")
@RequiredArgsConstructor
public class UserDebateServiceImpl implements UserDebateService {
    private final UserRepository userRepository;

    private final DebateRepository debateRepository;

    private final DebateHistoryService debateHistoryService;

    private final UserDebateRepository userDebateRepository;

    @Override
    @Transactional
    public UserDebate createUserDebate(UserDebateRegisterPostReq userDebateReq) {
        Debate debate = debateRepository.findById(userDebateReq.getDebateId()).orElseThrow(NoClassDefFoundError::new);
        User user = userRepository.findByUserEmail(userDebateReq.getUserEmail()).orElseThrow(NoSuchFieldError::new);
        UserDebate userDebate = makeUserDebate(userDebateReq, user, debate);
        UserDebate savedUserDebate = userDebateRepository.save(userDebate);
        Action action = Action.JOIN;
        debateHistoryService.createDebateHistory(action, debate, user);

        return userDebateRepository.save(userDebate);
    }

    @Override
    public void modifyUserDebateRole(String role) {
//        UserDebate userDebate = debateRepository.findById()
    }

    @Override
    public Map getUserDebate(User user) {
        List<UserDebate> histories = userDebateRepository.findAllByUserId(user.getId());
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getName());
        variables.put("email", user.getUserEmail());
        variables.put("histories",
                histories.stream()
                        .map(x -> {
                            Debate debate = x.getDebate();
                            long activeTime = Duration.between(debate.getCallStartTime(), debate.getCallEndTime()).toMinutes();
                            return UserDebateHistory.builder()
                                    .date(debate.getInsertedTime())
                                    .title(debate.getTitle())
                                    .debateId(debate.getId())
                                    .role(x.getRole())
                                    .activeTime(activeTime)
                                    .build();
                        }).collect(Collectors.toList()));
        variables.put("totalTime", histories.stream().mapToLong(x -> {
                    Debate debate = x.getDebate();
                    return Duration.between(debate.getCallStartTime(), debate.getCallEndTime()).toMinutes();
                }).
                sum());
        return variables;
    }

    @Override
    public Page<UserDebateHistory> getUserDebatePage(User user, Pageable pageable) {
        Page<UserDebate> allByUserIdOrderByPage = userDebateRepository.findAllByUserIdOrderByPage(user.getId(), pageable);

            Page<UserDebateHistory> userDebateHistory = allByUserIdOrderByPage.map(debate ->
                    UserDebateHistory.builder()
                            .title(debate.getDebate().getTitle())
                            .debateId(debate.getDebate().getId())
                            .role(debate.getRole())
                            .activeTime(Duration.between(debate.getDebate().getCallStartTime(), debate.getDebate().getCallEndTime()).toMinutes())
                            .date(debate.getDebate().getCallEndTime())
                            .build());

            return userDebateHistory;

    }


    private UserDebate makeUserDebate(UserDebateRegisterPostReq userDebateReq, User user, Debate debate) {
        UserDebate userDebate = new UserDebate();
        userDebate.setDebate(debate);
        userDebate.setUser(user);
        userDebate.setRole(userDebateReq.getRole());
        return userDebate;
    }
}
