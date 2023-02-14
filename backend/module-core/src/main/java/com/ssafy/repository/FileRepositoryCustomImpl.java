package com.ssafy.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.entity.rdbms.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import static com.ssafy.entity.rdbms.QFile.file;
public class FileRepositoryCustomImpl extends QuerydslRepositorySupport implements FileRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     */
    public FileRepositoryCustomImpl() { super(File.class); }

    @Override
    public void updateThumbnailState(long fileManagerId) {
        jpaQueryFactory.update(file)
                .set(file.deleted, true)
                .where(file.fileManager.id.eq(fileManagerId), file.savedFileName.containsIgnoreCase("_thumbnail"));
    }
}
