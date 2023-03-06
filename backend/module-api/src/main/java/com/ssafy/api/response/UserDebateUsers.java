package com.ssafy.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("UserDebateUsers")
public class UserDebateUsers {
    //    TODO : 다중 관점 고려 구현.
    //    @ApiModelProperty(name="토론내 관점", example="반려동물 보유세 필요하다.")
    //    Long perspectiveId;
    //    String perspectiveName;
    @ApiModelProperty(name="토론내 역할", example="찬성")
    String role;
    @ApiModelProperty(name="토론내 참여자(평가대상) 리스트")
    List<EvaluatedBase> evaluatedList;

}
