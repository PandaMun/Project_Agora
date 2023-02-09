package com.ssafy.api.service;

import com.ssafy.api.request.UserDebateRegisterPostReq;
import com.ssafy.entity.rdbms.UserDebate;

public interface UserDebateService {

    UserDebate createUserDebate(UserDebateRegisterPostReq userDebateReq);

    void modifyUserDebateRole(String role);
}
