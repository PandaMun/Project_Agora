package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserModifyPatchReq")
public class UserModifyPatchReq {

    @ApiModelProperty(name="유저 이메일", example="임하림")
    String userEmail;

    @ApiModelProperty(name="유저 이름", example="임하림")
    String name;

    @ApiModelProperty(name="유저 학교", example="기술개발팀")
    String department;

    @ApiModelProperty(name="유저 학년", example="임하림")
    int grade;

    @ApiModelProperty(name="유저 반", example="팀장")
    int classNum;

}
