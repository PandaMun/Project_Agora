package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFileManager is a Querydsl query type for FileManager
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFileManager extends EntityPathBase<FileManager> {

    private static final long serialVersionUID = -1776780939L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFileManager fileManager = new QFileManager("fileManager");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<File, QFile> files = this.<File, QFile>createList("files", File.class, QFile.class, PathInits.DIRECT2);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.util.Date> registTime = createDateTime("registTime", java.util.Date.class);

    public final QUser user;

    public QFileManager(String variable) {
        this(FileManager.class, forVariable(variable), INITS);
    }

    public QFileManager(Path<? extends FileManager> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFileManager(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFileManager(PathMetadata metadata, PathInits inits) {
        this(FileManager.class, metadata, inits);
    }

    public QFileManager(Class<? extends FileManager> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

