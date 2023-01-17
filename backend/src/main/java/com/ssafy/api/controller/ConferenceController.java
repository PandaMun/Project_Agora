package com.ssafy.api.controller;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.api.request.UserModifyPatchReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 회의 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "회의 API", tags = {"Conference"})
@RestController
@RequestMapping("/api/v1/conferences/")
public class ConferenceController {

	@Autowired
	ConferenceService conferenceService;

	@PostMapping()
	@ApiOperation(value = "회의 생성")
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) ConferenceRegisterPostReq conferenceRegisterPostReq) {

		Conference conference = conferenceService.createConference(conferenceRegisterPostReq);
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "회의 삭제")
	public ResponseEntity<BaseResponseBody> deleteConference(@ApiIgnore Authentication authentication,
			@PathVariable Long id) {

		conferenceService.deleteConference(id);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
	}
}