package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDebateHistory is a Querydsl query type for DebateHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDebateHistory extends EntityPathBase<DebateHistory> {

    private static final long serialVersionUID = 1041896007L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDebateHistory debateHistory = new QDebateHistory("debateHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<Action> action = createEnum("action", Action.class);

    public final QDebate debate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.time.LocalDateTime> insertedTime = createDateTime("insertedTime", java.time.LocalDateTime.class);

    public final QUser user;

    public QDebateHistory(String variable) {
        this(DebateHistory.class, forVariable(variable), INITS);
    }

    public QDebateHistory(Path<? extends DebateHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDebateHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDebateHistory(PathMetadata metadata, PathInits inits) {
        this(DebateHistory.class, metadata, inits);
    }

    public QDebateHistory(Class<? extends DebateHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.debate = inits.isInitialized("debate") ? new QDebate(forProperty("debate"), inits.get("debate")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

