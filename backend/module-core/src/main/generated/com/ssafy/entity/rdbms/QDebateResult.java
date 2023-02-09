package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDebateResult is a Querydsl query type for DebateResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDebateResult extends EntityPathBase<DebateResult> {

    private static final long serialVersionUID = 1701681162L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDebateResult debateResult = new QDebateResult("debateResult");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QDebate debate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath mvp = createString("mvp");

    public final StringPath summary = createString("summary");

    public QDebateResult(String variable) {
        this(DebateResult.class, forVariable(variable), INITS);
    }

    public QDebateResult(Path<? extends DebateResult> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDebateResult(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDebateResult(PathMetadata metadata, PathInits inits) {
        this(DebateResult.class, metadata, inits);
    }

    public QDebateResult(Class<? extends DebateResult> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.debate = inits.isInitialized("debate") ? new QDebate(forProperty("debate"), inits.get("debate")) : null;
    }

}

