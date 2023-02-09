package com.ssafy.repository;

import com.ssafy.entity.rdbms.Debate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 토론 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface DebateRepository extends JpaRepository<Debate, Long>, DebateRepositoryCustom {
}