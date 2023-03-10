package com.ssafy.repository;

import com.ssafy.entity.rdbms.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {

    Page<Faq> findAllByCategory(String category,Pageable pageable);
}
