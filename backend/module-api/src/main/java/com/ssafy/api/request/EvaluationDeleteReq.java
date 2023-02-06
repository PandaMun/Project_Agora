package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EvaluationDeleteRequest")
public class EvaluationDeleteReq {
    @ApiModelProperty(name="토론 ID", example="1")
    Long debateId;
    @ApiModelProperty(name="평가자 ID", example="130")
    Long evaluatorId;
    @ApiModelProperty(name="피평가자 ID", example="90")
    Long evaluatedId;
}
