package com.ssafy.api.controller;

import com.ssafy.api.request.DebateModifyPatchReq;
import com.ssafy.api.request.DebateModifyStatePatchReq;
import com.ssafy.api.request.DebateRegisterPostReq;
import com.ssafy.api.service.DebateService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.model.response.BaseResponseDataBody;
import com.ssafy.entity.rdbms.Debate;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 토론 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "토론 API", tags = {"Debate"})
@RestController
@RequestMapping("/api/v1/debates/")
@RequiredArgsConstructor
public class DebateController {

	private final DebateService debateService;

	@PostMapping()
	@ApiOperation(value = "토론 생성")
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) DebateRegisterPostReq debateRegisterPostReq) {

		Debate debate = debateService.createDebate(debateRegisterPostReq);

		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}

	@GetMapping()
	@ApiOperation(value = "토론 조회")
	public ResponseEntity<BaseResponseDataBody<Page<Debate>>> searchAll(@RequestParam String keyword,
												  @RequestParam String condition,
												  @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<Debate> debates = debateService.searchAll(keyword, condition, pageable);
		BaseResponseDataBody<Page<Debate>> response = BaseResponseDataBody.of("Success", 200, debates);
		return ResponseEntity.status(201).body(response);
	}

	@GetMapping("/{debateId}")
	@ApiOperation(value = "토론 아이디 조회")
	public ResponseEntity<BaseResponseDataBody<Debate>> search(@PathVariable long debateId){
		Debate debate = debateService.search(debateId);
		BaseResponseDataBody<Debate> response = BaseResponseDataBody.of("Success", 200, debate);
		return ResponseEntity.status(201).body(response);
	}

	@PatchMapping("/{debateId}")
	@ApiOperation(value = "토론 수정")
	public ResponseEntity<?> modifyDebate(@PathVariable long debateId, @RequestBody DebateModifyPatchReq debateModifyReq){
		try {
			debateService.updateDebate(debateId, debateModifyReq);
		} catch (NoSuchElementException e){
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "토론이 존재하지 않습니다"));
		} catch (Exception e){
			return ResponseEntity.status(500).body(BaseResponseBody.of(500, "서버 오류 발생"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}

	@PatchMapping("/state/{debateId}")
	@ApiOperation(value = "토론 상태 수정")
	public ResponseEntity<?> modifyDebateState(@PathVariable long debateId ,@RequestBody DebateModifyStatePatchReq debateModifyReq){
		try {
			debateService.updateDebateState(debateId, debateModifyReq);
		} catch (NoSuchElementException e){
			return ResponseEntity.status(404).body(BaseResponseBody.of(404, "토론이 존재하지 않습니다"));
		} catch (Exception e){
			return ResponseEntity.status(500).body(BaseResponseBody.of(500, "서버 오류 발생"));
		}
		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
	}



	@DeleteMapping("/{id}")
	@ApiOperation(value = "토론 삭제")
	public ResponseEntity<BaseResponseBody> deleteDebate(@ApiIgnore Authentication authentication,
			@PathVariable Long id) {

		debateService.deleteDebate(id);
		return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
	}
}
