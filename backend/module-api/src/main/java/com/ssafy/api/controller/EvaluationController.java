package com.ssafy.api.controller;

import com.ssafy.api.request.EvaluationRegisterPostReq;
import com.ssafy.api.response.EvaluationRes;
import com.ssafy.api.service.CommonCodeService;
import com.ssafy.api.service.EvaluationService;
import com.ssafy.common.auth.CustomUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.CommonCode;
import com.ssafy.entity.rdbms.Evaluation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 상호평가 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "토론 상호 평가 API", tags = {"Evaluation"})
@RestController
@RequestMapping("/api/v1/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;
    
    private final CommonCodeService commonCodeService;

    @PostMapping()
    @ApiOperation(value = "토론 상호 평가 생성")
    public ResponseEntity<? extends BaseResponseBody> register(@ApiIgnore Authentication authentication,
            @RequestBody @ApiParam(value="상호 평가 정보", required = true) EvaluationRegisterPostReq evaluationRegisterPostReq) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        Evaluation evaluation = evaluationService.createEvaluation(evaluationRegisterPostReq, userId);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
    }

    @DeleteMapping("/{evaluationId}")
    @ApiOperation(value = " 토론 상호 평가 삭제", notes ="토론에서 특정 유저간 평가내용(1 row)을 삭제한다.")
    public ResponseEntity<? extends BaseResponseBody> delete (
            @PathVariable Long evaluationId){

        evaluationService.deleteEvaluation(evaluationId);
        return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
    }

    @GetMapping("")
    @ApiOperation(value="로그인 유저의 전체 토론 상호 평가 조회", notes="")
    public ResponseEntity<?> getEvaluations(@ApiIgnore Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        List<Evaluation> evaluationList = evaluationService.getEvaluationList(userId);
        if(evaluationList.isEmpty())
            return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));

        return ResponseEntity.status(200).body(EvaluationRes.of(evaluationList, userId));
    }

    @GetMapping("/debates/{debateId}")
    @ApiOperation(value="특정 토론의 상호 평가 결과 조회", notes="")
    public ResponseEntity<?> getEvaluationsByDebateId(@ApiIgnore Authentication authentication, @PathVariable Long debateId){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();

        List<Evaluation> evaluationList = evaluationService.getEvaluationListByDebateId(userId, debateId);
        if(evaluationList.isEmpty())
            return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));

        return ResponseEntity.status(200).body(EvaluationRes.of(evaluationList, userId));
    }


    // 계층 구조를 유지한채 데이터를 보내는 방식.
    @GetMapping("/questions")
    @ApiOperation(value="토론 평가 문항 조회")
    public ResponseEntity<CommonCode> getEvaluationQuestions(){
        CommonCode questionList = commonCodeService.getQuestionList();
        return ResponseEntity.status(200).body(questionList);
    }
}
