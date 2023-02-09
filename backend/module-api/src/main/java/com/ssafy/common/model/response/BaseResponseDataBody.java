package com.ssafy.common.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 서버 요청에대한 기본 응답값(바디) 정의.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@ApiModel("BaseResponseBody")
public class BaseResponseDataBody<T> {
	@ApiModelProperty(name="응답 메시지", example = "정상")
	private String message;
	@ApiModelProperty(name="응답 코드", example = "200")
	private int statusCode;
	@ApiModelProperty(name="응답 정보", example = "{\"a\":\"A\"}")
	private T data;

}
