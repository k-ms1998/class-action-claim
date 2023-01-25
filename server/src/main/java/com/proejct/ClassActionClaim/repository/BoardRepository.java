package com.proejct.ClassActionClaim.repository;

import com.proejct.ClassActionClaim.domain.Board;
import com.proejct.ClassActionClaim.repository.custom.BoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
