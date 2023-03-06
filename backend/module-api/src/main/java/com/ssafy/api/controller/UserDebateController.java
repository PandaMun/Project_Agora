package com.ssafy.api.controller;


import com.ssafy.api.request.UserDebateRegisterPostReq;
import com.ssafy.api.response.DebateRes;
import com.ssafy.api.response.UserDebateHistory;
import com.ssafy.api.response.UserDebateUsers;
import com.ssafy.api.service.UserDebateService;
import com.ssafy.common.auth.CustomUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.model.response.BaseResponseDataBody;
import com.ssafy.entity.rdbms.UserDebate;
import com.ssafy.repository.UserDebateRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Api(value = "유저-토론 API", tags = {"UserDebate"})
@RestController
@RequestMapping("/api/v1/userDebates")
@RequiredArgsConstructor
public class UserDebateController {

    private final UserDebateService userDebateService;
    private final UserDebateRepository userDebateRepository;

    @PostMapping()
    @ApiOperation(value = "유저 토론 생성")
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value = "토론 참가 신청", required = true) UserDebateRegisterPostReq userDebateReq) {

        UserDebate userDebate = userDebateService.createUserDebate(userDebateReq);

        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
    }

    @GetMapping()
    @ApiOperation(value = "유저 토론 조회")
    public ResponseEntity<?> getUserDebateHistory(
            @ApiIgnore Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Map userDebate = userDebateService.getUserDebate(userDetails.getUser());
        return ResponseEntity.status(201).body(userDebate);
    }
    @GetMapping("/pageable")
    @ApiOperation(value = "유저 토론 조회")
    public ResponseEntity<?> getUserDebateHistoryPageable(
            @ApiParam(value="페이지 정보", required = true) Pageable pageable,
            @ApiIgnore Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Page<UserDebateHistory> userDebatePage = userDebateService.getUserDebatePage(userDetails.getUser(), pageable);
        return ResponseEntity.status(201).body(userDebatePage);
    }

    @GetMapping("/debates/{debateId}/users")
    @ApiOperation(value = "특정 토론 참여 유저 토론 아이디 조회")
    public ResponseEntity<BaseResponseDataBody<List<UserDebateUsers>>> searchUsers(@PathVariable long debateId){
        List<UserDebateUsers> userDebateUsers = userDebateService.getUserDebateUsersList(debateId);

        BaseResponseDataBody<List<UserDebateUsers>> response = BaseResponseDataBody.of("Success", 200, userDebateUsers);
        return ResponseEntity.status(200).body(response);
    }
}
