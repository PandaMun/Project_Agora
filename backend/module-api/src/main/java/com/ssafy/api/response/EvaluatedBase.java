package com.ssafy.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 토론 상호평가 대상 조회 API ([GET] /api/v1/vote/userDebates/debates/{:debateId}/users) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("Evaluated")
public class EvaluatedBase {
    @ApiModelProperty(name="평가 대상 이메일")
    String userEmail;
    @ApiModelProperty(name="평가 대상 이름")
    String userName;
}
