package com.ssafy.api.service;

import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.Perspective;
import com.ssafy.repository.PerspectiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("perspectiveService")
@RequiredArgsConstructor
public class PerspectiveServiceImpl implements PerspectiveService {

    private final PerspectiveRepository perspectiveRepository;

    @Override
    public List<Perspective> createPerspective(List<String> perspectiveNames, Debate debate) {
        List<Perspective> perspectives = perspectiveNames
                .stream()
                .map(perspectiveName -> {
                    Perspective perspective = new Perspective();
                    perspective.setDebate(debate);
                    perspective.setName(perspectiveName);
                    return perspective;
                })
                .collect(Collectors.toList());
        perspectiveRepository.saveAll(perspectives);
        return perspectives;
    }

    @Override
    public List<Perspective> searchByDebateId(long debatedId) {
        return perspectiveRepository.findByDebateID(debatedId);
    }
}
