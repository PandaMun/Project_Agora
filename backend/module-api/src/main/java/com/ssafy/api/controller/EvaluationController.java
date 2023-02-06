package com.ssafy.api.controller;

import com.ssafy.api.request.EvaluationRegisterPostReq;
import com.ssafy.api.response.EvaluationRes;
import com.ssafy.api.service.EvaluationService;
import com.ssafy.common.auth.CustomUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.entity.rdbms.Evaluation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
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

    @PostMapping()
    @ApiOperation(value = "토론 상호 평가 생성")
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="상호 평가 정보", required = true) EvaluationRegisterPostReq evaluationRegisterPostReq) {

        Evaluation evaluation = evaluationService.createEvaluation(evaluationRegisterPostReq);
        return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
    }

    @DeleteMapping("/{evaluationId}")
    @ApiOperation(value = " 토론 상호 평가 삭제", notes ="토론에서 특정 유저간 평가내용(1 row)을 삭제한다.")
    public ResponseEntity<? extends BaseResponseBody> delete (
            @PathVariable Long evaluationId){

        evaluationService.deleteEvaluation(evaluationId);
        return ResponseEntity.status(204).body(BaseResponseBody.of(204, "Success"));
    }

    @GetMapping("/users/{:userId}")
    @ApiOperation(value="토론 상호 평가 조회", notes="")
    public ResponseEntity<EvaluationRes> getEvaluations(@ApiIgnore Authentication authentication,
                                                        @PathVariable String userId)
    {
        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        if (!userId.equals(userDetails.getUsername())) {
            return ResponseEntity.badRequest().build();
        }

        List<Evaluation> evaluationList = evaluationService.getEvaluationList(userId);

        return ResponseEntity.status(200).body(EvaluationRes.of(evaluationList));
    }
}
