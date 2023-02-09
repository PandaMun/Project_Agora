package com.ssafy.repository;

import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.DebateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebateHistoryRepository  extends JpaRepository<DebateHistory, Long>  {
}
