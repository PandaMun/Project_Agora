package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVote is a Querydsl query type for Vote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVote extends EntityPathBase<Vote> {

    private static final long serialVersionUID = 974348422L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVote vote = new QVote("vote");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QDebate debate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QUser mvpUser;

    public final QPerspective perspective;

    public final QUser user;

    public QVote(String variable) {
        this(Vote.class, forVariable(variable), INITS);
    }

    public QVote(Path<? extends Vote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVote(PathMetadata metadata, PathInits inits) {
        this(Vote.class, metadata, inits);
    }

    public QVote(Class<? extends Vote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.debate = inits.isInitialized("debate") ? new QDebate(forProperty("debate"), inits.get("debate")) : null;
        this.mvpUser = inits.isInitialized("mvpUser") ? new QUser(forProperty("mvpUser"), inits.get("mvpUser")) : null;
        this.perspective = inits.isInitialized("perspective") ? new QPerspective(forProperty("perspective"), inits.get("perspective")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

