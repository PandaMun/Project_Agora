package com.ssafy.api.service;

import com.ssafy.api.request.UserDebateRegisterPostReq;
import com.ssafy.api.response.DebateRes;
import com.ssafy.api.response.UserDebateHistory;
import com.ssafy.entity.rdbms.User;
import com.ssafy.entity.rdbms.UserDebate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UserDebateService {

    UserDebate createUserDebate(UserDebateRegisterPostReq userDebateReq);

    void modifyUserDebateRole(String role);

    Map getUserDebate(User user);

    Page<UserDebateHistory> getUserDebatePage(User user, Pageable pageable);

}
