package com.ssafy.repository;

import com.ssafy.entity.rdbms.Debate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DebateRepositoryCustom {
    Page<Debate> findDebateBySearchCondition(String keyword, String condition, Pageable pageable);
}
