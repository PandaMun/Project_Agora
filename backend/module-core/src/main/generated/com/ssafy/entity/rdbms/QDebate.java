package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDebate is a Querydsl query type for Debate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDebate extends EntityPathBase<Debate> {

    private static final long serialVersionUID = -479133299L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDebate debate = new QDebate("debate");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> callEndTime = createDateTime("callEndTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> callStartTime = createDateTime("callStartTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> category = createNumber("category", Long.class);

    public final StringPath debateMode = createString("debateMode");

    public final StringPath debateModeOption = createString("debateModeOption");

    public final QDebateResult debateResult;

    public final StringPath description = createString("description");

    public final QFileManager fileManager;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> insertedTime = createDateTime("insertedTime", java.time.LocalDateTime.class);

    public final BooleanPath moderatorOnOff = createBoolean("moderatorOnOff");

    public final QUser owner;

    public final StringPath state = createString("state");

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public QDebate(String variable) {
        this(Debate.class, forVariable(variable), INITS);
    }

    public QDebate(Path<? extends Debate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDebate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDebate(PathMetadata metadata, PathInits inits) {
        this(Debate.class, metadata, inits);
    }

    public QDebate(Class<? extends Debate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.debateResult = inits.isInitialized("debateResult") ? new QDebateResult(forProperty("debateResult"), inits.get("debateResult")) : null;
        this.fileManager = inits.isInitialized("fileManager") ? new QFileManager(forProperty("fileManager"), inits.get("fileManager")) : null;
        this.owner = inits.isInitialized("owner") ? new QUser(forProperty("owner"), inits.get("owner")) : null;
    }

}

