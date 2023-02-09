package com.ssafy.api.service;

import com.ssafy.api.request.DebateModifyPatchReq;
import com.ssafy.api.request.DebateModifyStatePatchReq;
import com.ssafy.api.request.DebateRegisterPostReq;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.Perspective;
import com.ssafy.entity.rdbms.User;
import com.ssafy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 토론 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("debateService")
@RequiredArgsConstructor
public class DebateServiceImpl implements DebateService {

    private final UserRepository userRepository;

    private final DebateRepository debateRepository;

    private final DebateResultRepository debateResultRepository;

    private final DebateRepositoryCustomImpl debateRepositoryCustom;

    private final PerspectiveServiceImpl perspectiveService;

    @Override
    @Transactional
    public Debate createDebate(DebateRegisterPostReq debateRegisterPostReq) {
        User owner = userRepository.findById(debateRegisterPostReq.getOwnerId())
                .orElseThrow(NoSuchElementException::new);
        Debate debate = makeDebate(debateRegisterPostReq, owner);
        Debate savedDebate = debateRepository.save(debate);

        perspectiveService.createPerspective(debateRegisterPostReq.getPerspectiveNames(), savedDebate);
        return savedDebate;
    }
    @Override
    public Page<Debate> searchAll(String keyword, String condition, Pageable pageable) {
        return debateRepository.findDebateBySearchCondition(keyword, condition, pageable);
    }

    @Override
    public Debate search(long debateId) {
        return debateRepository.findById(debateId).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void updateDebate(long debateId, DebateModifyPatchReq debateModifyReq) {
        Debate debate = debateRepository.findById(debateId).orElseThrow(NoSuchElementException::new);
        debate.setTitle(debateModifyReq.getTitle());
        debate.setDescription(debateModifyReq.getDescription());
        debate.setCategory(debateModifyReq.getCategory());
        debate.setModeratorOnOff(debateModifyReq.getModeratorOnOff());
        debate.setDebateMode(debateModifyReq.getDebateMode());
        debate.setCallStartTime(debateModifyReq.getCallStartTime());
        debate.setCallEndTime(debateModifyReq.getCallEndTime());
        debate.setDebateModeOption(debateModifyReq.getDebateModeOption());
        debateRepository.save(debate);
    }

    @Override
    public void updateDebateState(long debateId, DebateModifyStatePatchReq debateModifyStateReq) {
        Debate debate = debateRepository.findById(debateId).orElseThrow(NoSuchElementException::new);
        debate.setState(debateModifyStateReq.getState());
        debateRepository.save(debate);
    }

    private Debate makeDebate(DebateRegisterPostReq debateRegisterPostReq, User owner) {
        Debate debate = new Debate();
        debate.setOwner(owner);
        debate.setCategory(debateRegisterPostReq.getCategory());
        debate.setTitle(debateRegisterPostReq.getTitle());
        debate.setDescription(debateRegisterPostReq.getDescription());
        debate.setModeratorOnOff(debateRegisterPostReq.getModeratorOnOff());
        debate.setDebateMode(debate.getDebateMode());
        debate.setThumbnailUrl(debateRegisterPostReq.getThumbnailUrl());
        debate.setState(debate.getState());
        debate.setInsertedTime(debateRegisterPostReq.getInsertedTime());
        debate.setCallStartTime(debateRegisterPostReq.getCallStartTime());
        debate.setCallEndTime(debateRegisterPostReq.getCallEndTime());
        debate.setDebateModeOption(debateRegisterPostReq.getDebateModeOption());
        return debate;
    }

    @Override
    public void deleteDebate(Long id) {
        Debate debate = debateRepository.findById(id).orElseThrow(NoSuchElementException::new);
        debateRepository.delete(debate);
    }
}
