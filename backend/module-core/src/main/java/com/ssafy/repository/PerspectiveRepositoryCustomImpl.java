package com.ssafy.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.entity.rdbms.Debate;
import com.ssafy.entity.rdbms.Perspective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.ssafy.entity.rdbms.QPerspective.perspective;

import java.util.List;
import java.util.Objects;


public class PerspectiveRepositoryCustomImpl extends QuerydslRepositorySupport implements PerspectiveRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     */
    public PerspectiveRepositoryCustomImpl() {
        super(Perspective.class);
    }

    @Override
    public List<Perspective> findByDebateID(long debateId) {
        List<Perspective> perspectives = jpaQueryFactory.selectFrom(perspective)
                .where(perspective.debate.id.eq(debateId)).fetch();
        return perspectives;
    }
}
