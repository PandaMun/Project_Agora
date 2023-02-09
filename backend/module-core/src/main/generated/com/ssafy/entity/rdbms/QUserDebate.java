package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserDebate is a Querydsl query type for UserDebate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserDebate extends EntityPathBase<UserDebate> {

    private static final long serialVersionUID = -1768102088L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserDebate userDebate = new QUserDebate("userDebate");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QDebate debate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath role = createString("role");

    public final QUser user;

    public QUserDebate(String variable) {
        this(UserDebate.class, forVariable(variable), INITS);
    }

    public QUserDebate(Path<? extends UserDebate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserDebate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserDebate(PathMetadata metadata, PathInits inits) {
        this(UserDebate.class, metadata, inits);
    }

    public QUserDebate(Class<? extends UserDebate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.debate = inits.isInitialized("debate") ? new QDebate(forProperty("debate"), inits.get("debate")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

