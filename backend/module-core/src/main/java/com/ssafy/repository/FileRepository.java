package com.ssafy.repository;

import com.ssafy.entity.rdbms.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long>, FileRepositoryCustom {


    @Query("select F from File F where F.deleted = false and F.fileManager.id = :fileId")
    File findProfileImgUrl(@Param("fileId") Long fileId);
}
