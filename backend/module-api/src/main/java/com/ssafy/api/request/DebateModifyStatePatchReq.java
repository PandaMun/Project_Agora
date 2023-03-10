package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("DebateModifyStatePatchReq")
public class DebateModifyStatePatchReq {

    @ApiModelProperty(name="토론 상태")
    String state;
}
