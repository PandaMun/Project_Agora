package com.ssafy.repository;

import com.ssafy.entity.rdbms.Perspective;

import java.util.List;

public interface PerspectiveRepositoryCustom {
    List<Perspective> findByDebateID(long debateId);
}
