package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.repository.ConferenceCategoryRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 *	회의 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("conferenceService")
public class ConferenceServiceImpl implements ConferenceService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	ConferenceRepository conferenceRepository;

	@Autowired
	ConferenceCategoryRepository conferenceCategoryRepository;

	@Override
	public Conference createConference(ConferenceRegisterPostReq conferenceRegisterInfo) {
		Conference conference = new Conference();
		conference.setOwner(userRepository.findById(conferenceRegisterInfo.getOwnerId()).orElseThrow(NoSuchElementException::new));
		conference.setConferenceCategory(conferenceCategoryRepository.findById(conferenceRegisterInfo.getConferenceCategory()).get());
		conference.setCallStartTime(conferenceRegisterInfo.getCallStartTime());
		conference.setCallEndTime(conferenceRegisterInfo.getCallEndTime());
		conference.setTitle(conferenceRegisterInfo.getTitle());
		conference.setThumbnailUrl(conferenceRegisterInfo.getThumbnailUrl());
		conference.setDescription(conferenceRegisterInfo.getDescription());
		return conferenceRepository.save(conference);
	}

	@Override
	public void deleteConference(Long id) {
		Conference conference = conferenceRepository.findById(id).orElseThrow(NoSuchElementException::new);
		conferenceRepository.delete(conference);
	}

}