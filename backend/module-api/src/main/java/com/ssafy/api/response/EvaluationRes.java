package com.ssafy.api.response;

import com.ssafy.entity.rdbms.Evaluation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 토론 평가 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("EvaluationResponse")
public class EvaluationRes {
    @ApiModelProperty(name="토론 Id")
    String debateId;
    @ApiModelProperty(name="평가 Id")
    String evaluatedId;
    @ApiModelProperty(name="평가 내용 취합 결과", example="[1:[{2:5.0}, {3:4.5}], 4:[5:2.2,6:4.2], 7:[8:3.0,9:2.0]]")
    ArrayList<Map<Long, ArrayList<Map<Long, Float>>>> detailTotal;

    public static EvaluationRes of(List<Evaluation> evaluationList){
        return null;
    }
}
