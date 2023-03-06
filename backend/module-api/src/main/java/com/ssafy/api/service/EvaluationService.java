package com.ssafy.api.service;

import com.ssafy.api.request.DebateRegisterPostReq;
import com.ssafy.api.request.EvaluationDeleteReq;
import com.ssafy.api.request.EvaluationRegisterPostReq;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.Evaluation;

import java.util.List;

public interface EvaluationService {
    Evaluation createEvaluation(EvaluationRegisterPostReq evaluationRegisterPostReq, String userId);
    void deleteEvaluation(Long evaluationId);

    List<Evaluation> getEvaluationList(String userId);

    List<Evaluation> getEvaluationListByDebateId(String userId, Long debateId);
}
