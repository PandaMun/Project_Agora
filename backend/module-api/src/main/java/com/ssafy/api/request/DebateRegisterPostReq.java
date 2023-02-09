package com.ssafy.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 토론 생성 API ([POST] /api/v1/debates) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("DebateRegisterPostReq")
public class DebateRegisterPostReq {

	@ApiModelProperty(name="방장의 고유 ID")
	long ownerId;

	@ApiModelProperty(name="토론 카테고리 ID")
	long category;

	@CreationTimestamp
	@ApiModelProperty(name="토론 생성시간", example="2022-01-11 13:00:00")
	LocalDateTime insertedTime;

	@ApiModelProperty(name="토론 시작시간", example="2022-01-11 13:00:00")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime callStartTime;

	@ApiModelProperty(name="토론 종료시간", example="2022-01-11 14:00:00")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime callEndTime;

	@ApiModelProperty(name="썸네일 URL")
	String thumbnailUrl;

	@ApiModelProperty(name="토론 이름")
	String title;

	@ApiModelProperty(name="토론 설명")
	String description;

	@ApiModelProperty(name="토론 활성화 상태")
	String state;

	@ApiModelProperty(name="토론 모드")
	String debateMode;

	@ApiModelProperty(name="사회자 참여 여부")
	Boolean moderatorOnOff;

	@ApiModelProperty(name="토론 옵션")
	String debateModeOption;

	@ApiModelProperty(name="관점 리스트")
	List<String> perspectiveNames;
}
