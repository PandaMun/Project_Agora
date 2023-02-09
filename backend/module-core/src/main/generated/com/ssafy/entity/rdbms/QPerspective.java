package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPerspective is a Querydsl query type for Perspective
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPerspective extends EntityPathBase<Perspective> {

    private static final long serialVersionUID = -7871200L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPerspective perspective = new QPerspective("perspective");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QDebate debate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public QPerspective(String variable) {
        this(Perspective.class, forVariable(variable), INITS);
    }

    public QPerspective(Path<? extends Perspective> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPerspective(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPerspective(PathMetadata metadata, PathInits inits) {
        this(Perspective.class, metadata, inits);
    }

    public QPerspective(Class<? extends Perspective> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.debate = inits.isInitialized("debate") ? new QDebate(forProperty("debate"), inits.get("debate")) : null;
    }

}

