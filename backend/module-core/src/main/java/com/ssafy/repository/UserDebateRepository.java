package com.ssafy.repository;

import com.ssafy.entity.rdbms.UserDebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDebateRepository extends JpaRepository<UserDebate, Long> {
}
