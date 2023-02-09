package com.ssafy.api.service;

import com.ssafy.api.request.DebateModifyPatchReq;
import com.ssafy.api.request.DebateModifyStatePatchReq;
import com.ssafy.api.request.DebateRegisterPostReq;
import com.ssafy.entity.rdbms.Debate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 *	토론 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface DebateService {
	Debate createDebate(DebateRegisterPostReq debateRegisterPostReq);

	Page<Debate> searchAll(String keyword, String condition, Pageable pageable);

	Debate search(long debateId);

	void updateDebate(long debateId, DebateModifyPatchReq debateModifyReq);

	void updateDebateState(long debateId, DebateModifyStatePatchReq debateModifyStateReq);

	void deleteDebate(Long id);
}
