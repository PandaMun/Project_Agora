package com.ssafy.entity.rdbms;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 974322023L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> classNum = createNumber("classNum", Integer.class);

    public final ListPath<DebateHistory, QDebateHistory> debateHistories = this.<DebateHistory, QDebateHistory>createList("debateHistories", DebateHistory.class, QDebateHistory.class, PathInits.DIRECT2);

    public final ListPath<Debate, QDebate> debates = this.<Debate, QDebate>createList("debates", Debate.class, QDebate.class, PathInits.DIRECT2);

    public final StringPath department = createString("department");

    public final QFileManager fileManager;

    public final NumberPath<Integer> grade = createNumber("grade", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath position = createString("position");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final ListPath<UserDebate, QUserDebate> userDebates = this.<UserDebate, QUserDebate>createList("userDebates", UserDebate.class, QUserDebate.class, PathInits.DIRECT2);

    public final StringPath userEmail = createString("userEmail");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fileManager = inits.isInitialized("fileManager") ? new QFileManager(forProperty("fileManager"), inits.get("fileManager")) : null;
    }

}

