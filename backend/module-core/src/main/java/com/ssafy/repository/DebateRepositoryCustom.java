package com.ssafy.repository;

import com.ssafy.entity.rdbms.Debate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DebateRepositoryCustom {
    Page<Debate> findDebateBySearchCondition(String keyword, String condition, Pageable pageable, List<Long> categoryList);
}
