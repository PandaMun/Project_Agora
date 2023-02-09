package com.ssafy.api.service;

import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.Perspective;

import java.util.List;

public interface PerspectiveService {
     List<Perspective> createPerspective(List<String> perspectiveNames, Debate debate);

     List<Perspective> searchByDebateId(long debatedId);
}
