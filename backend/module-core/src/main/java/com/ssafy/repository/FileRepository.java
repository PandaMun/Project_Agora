package com.ssafy.repository;

import com.ssafy.entity.rdbms.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {


    @Query("select F from File F where F.deleted = false ")

    File findProfileImgUrl(Long fileId);
}
