package com.proejct.ClassActionClaim.repository.custom.customImpl;

import com.proejct.ClassActionClaim.domain.Claim;
import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.QClaim;
import com.proejct.ClassActionClaim.domain.enums.ClaimType;
import com.proejct.ClassActionClaim.repository.custom.ClaimRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class ClaimRepositoryCustomImpl implements ClaimRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<Claim> findByLectureAndClaimType(Lecture lecture, ClaimType claimType) {
        QClaim claim = QClaim.claim;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        return queryFactory
                .select(claim)
                .from(claim)
                .where(claim.lecture.eq(lecture), claim.claimType.eq(claimType))
                .orderBy(claim.upVotes.desc())
                .fetch();
    }
}
