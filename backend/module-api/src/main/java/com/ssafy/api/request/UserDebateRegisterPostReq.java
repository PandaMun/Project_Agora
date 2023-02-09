package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ApiModel("UserDebateRegisterPostReq")
public class UserDebateRegisterPostReq {

    @ApiModelProperty(name = "토론 아이디")
    long debateId;

    @ApiModelProperty(name = "회원 이메일")
    String userEmail;

    @ApiModelProperty(name = "역할")
    String role;
}
