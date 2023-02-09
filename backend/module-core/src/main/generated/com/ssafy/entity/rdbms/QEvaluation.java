package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEvaluation is a Querydsl query type for Evaluation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvaluation extends EntityPathBase<Evaluation> {

    private static final long serialVersionUID = -2057932840L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvaluation evaluation = new QEvaluation("evaluation");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    public final QDebate debate;

    public final QUser evaluated;

    public final QUser evaluator;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public QEvaluation(String variable) {
        this(Evaluation.class, forVariable(variable), INITS);
    }

    public QEvaluation(Path<? extends Evaluation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvaluation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvaluation(PathMetadata metadata, PathInits inits) {
        this(Evaluation.class, metadata, inits);
    }

    public QEvaluation(Class<? extends Evaluation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.debate = inits.isInitialized("debate") ? new QDebate(forProperty("debate"), inits.get("debate")) : null;
        this.evaluated = inits.isInitialized("evaluated") ? new QUser(forProperty("evaluated"), inits.get("evaluated")) : null;
        this.evaluator = inits.isInitialized("evaluator") ? new QUser(forProperty("evaluator"), inits.get("evaluator")) : null;
    }

}

