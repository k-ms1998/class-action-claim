package com.proejct.ClassActionClaim.repository;

import com.proejct.ClassActionClaim.domain.Claim;
import com.proejct.ClassActionClaim.repository.custom.ClaimRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long>, ClaimRepositoryCustom {
}
