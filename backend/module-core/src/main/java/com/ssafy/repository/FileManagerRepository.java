package com.ssafy.repository;

import com.ssafy.entity.rdbms.File;
import com.ssafy.entity.rdbms.FileManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileManagerRepository extends JpaRepository<FileManager, Long> {


}
