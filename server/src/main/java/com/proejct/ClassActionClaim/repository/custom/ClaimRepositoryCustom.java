package com.proejct.ClassActionClaim.repository.custom;

import com.proejct.ClassActionClaim.domain.Claim;
import com.proejct.ClassActionClaim.domain.Lecture;
import com.proejct.ClassActionClaim.domain.enums.ClaimType;

import java.util.List;

public interface ClaimRepositoryCustom {

    List<Claim> findByLectureAndClaimType(Lecture lecture, ClaimType claimType);
}
