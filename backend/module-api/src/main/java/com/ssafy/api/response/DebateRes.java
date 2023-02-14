package com.ssafy.api.response;

import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.Faq;
import com.ssafy.entity.rdbms.File;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@ApiModel("DebateResponse")
public class DebateRes {
    @ApiModelProperty(name = "토론 아이디")
    private long debateId;

    @ApiModelProperty(name = "방장의 이메일")
    private String ownerEmail;

    @ApiModelProperty(name = "방장의 이름")
    private String ownerName;

    @ApiModelProperty(name = "방장의 소속")
    private String ownerDepartment;

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

    @ApiModelProperty(name = "토론 옵션")
    private List<FileRes> fileList;

    public static DebateRes of(Debate debate) {
        DebateRes res = new DebateRes();
        res.setDebateId(debate.getId());
        res.setOwnerEmail(debate.getOwner().getUserEmail());
        res.setOwnerName(debate.getOwner().getName());
        res.setOwnerDepartment(debate.getOwner().getDepartment());
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
        if(debate.getFileManager() != null){
            res.setFileList(new FileRes().toDtoList(debate.getFileManager().getFiles()));
        }
        return res;
    }
    public Page<DebateRes> toDtoList(Page<Debate> debates) {
        Page<DebateRes> DebateResList = debates.map(DebateRes::of);
        return DebateResList;
    }

}
