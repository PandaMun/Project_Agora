package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("DebateModifyPatchReq")
public class DebateModifyPatchReq {

    @ApiModelProperty(name="토론 제목")
    private String title;

    @ApiModelProperty(name="토론 설명")
    private String description;

    @ApiModelProperty(name="토론 카테고리")
    private Long category;

    @ApiModelProperty(name="토론 사회자 여부")
    private Boolean moderatorOnOff;

    @ApiModelProperty(name="토론 모드")
    private String debateMode;

    @ApiModelProperty(name="토론 시작 시간")
    private LocalDateTime callStartTime;

    @ApiModelProperty(name="토론 종료 시간")
    private LocalDateTime callEndTime;

    @ApiModelProperty(name="토론 설정")
    private String debateModeOption;
}
