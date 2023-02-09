package com.ssafy.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.entity.rdbms.Debate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import static com.ssafy.entity.rdbms.QDebate.debate;

import java.util.List;
import java.util.Objects;

@Repository
public class DebateRepositoryCustomImpl extends QuerydslRepositorySupport implements DebateRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     */
    public DebateRepositoryCustomImpl() {
        super(Debate.class);
    }

    @Override
    public Page<Debate> findDebateBySearchCondition(String keyword, String condition, Pageable pageable) {
        JPAQuery<Debate> query = jpaQueryFactory.selectFrom(debate)
                .where(eqKeyword(keyword, condition));

        List<Debate> debates = Objects.requireNonNull(this.getQuerydsl())
                .applyPagination(pageable, query)
                .fetch();

        return new PageImpl<Debate>(debates, pageable, query.fetch().size());
    }


    private BooleanExpression eqKeyword(String keyword, String condition){
        if(keyword == null || keyword.isEmpty() || condition == null || condition.isEmpty()){
            return null;
        }
        if(condition.equals("owner")){
            return debate.owner.userEmail.eq(keyword);
        }else{
            return debate.title.containsIgnoreCase("keyword");
        }
    }
}
