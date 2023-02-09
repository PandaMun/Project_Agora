package com.ssafy.api.controller;


import com.ssafy.api.request.UserDebateRegisterPostReq;
import com.ssafy.api.service.UserDebateService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.UserDebate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "유저-토론 API", tags = {"UserDebate"})
@RestController
@RequestMapping("/api/v1/userDebates/")
@RequiredArgsConstructor
public class UserDebateController {

    private final UserDebateService userDebateService;

    @PostMapping()
    @ApiOperation(value = "유저 토론 생성")
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value = "토론 참가 신청", required = true) UserDebateRegisterPostReq userDebateReq) {

        UserDebate userDebate = userDebateService.createUserDebate(userDebateReq);

        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
    }

}
