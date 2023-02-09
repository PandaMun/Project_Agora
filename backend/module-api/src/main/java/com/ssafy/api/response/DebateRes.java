package com.ssafy.api.response;

import com.ssafy.entity.rdbms.Debate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("DebateResponse")
public class DebateRes {

    @ApiModelProperty(name = "방장의 고유 ID")
    private long ownerId;

    @ApiModelProperty(name = "토론 카테고리 ID")
    private long category;

    @ApiModelProperty(name = "토론 생성시간", example = "2022-01-11 13:00:00")
    private LocalDateTime insertedTime;

    @ApiModelProperty(name = "토론 시작시간", example = "2022-01-11 13:00:00")
    private LocalDateTime callStartTime;

    @ApiModelProperty(name = "토론 종료시간", example = "2022-01-11 14:00:00")
    private LocalDateTime callEndTime;

    @ApiModelProperty(name = "썸네일 URL")
    private String thumbnailUrl;

    @ApiModelProperty(name = "토론 이름")
    private String title;

    @ApiModelProperty(name = "토론 설명")
    private String description;

    @ApiModelProperty(name = "토론 활성화 상태")
    private String state;

    @ApiModelProperty(name = "토론 모드")
    private String debateMode;

    @ApiModelProperty(name = "사회자 참여 여부")
    private Boolean moderatorOnOff;

    @ApiModelProperty(name = "토론 옵션")
    private String debateModeOption;

    public static DebateRes of(Debate debate) {
        DebateRes res = new DebateRes();
        res.setOwnerId(debate.getOwner().getId());
        res.setCategory(debate.getCategory());
        res.setInsertedTime(debate.getInsertedTime());
        res.setCallStartTime(debate.getCallStartTime());
        res.setCallEndTime(debate.getCallEndTime());
        res.setThumbnailUrl(debate.getThumbnailUrl());
        res.setTitle(debate.getTitle());
        res.setDescription(debate.getDescription());
        res.setState(debate.getState());
        res.setDebateMode(debate.getDebateMode());
        res.setModeratorOnOff(debate.getModeratorOnOff());
        res.setDebateModeOption(debate.getDebateModeOption());
        return res;
    }

}
