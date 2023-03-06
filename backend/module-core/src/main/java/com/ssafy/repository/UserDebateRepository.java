package com.ssafy.repository;

import com.ssafy.entity.rdbms.UserDebate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDebateRepository extends JpaRepository<UserDebate, Long> {

    @Query("select ud from UserDebate ud left join fetch ud.debate where ud.user.id = :userId")
    List<UserDebate> findAllByUserId(@Param(value = "userId") Long userId);

    @Query("select ud from UserDebate ud where ud.user.id = :userId")
    Page<UserDebate> findAllByUserIdOrderByPage(@Param(value = "userId") Long userId, Pageable pageable);


    List<UserDebate> findAllByDebateId(long debateId);
}
