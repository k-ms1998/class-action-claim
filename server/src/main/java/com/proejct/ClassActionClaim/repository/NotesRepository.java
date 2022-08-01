package com.proejct.ClassActionClaim.repository;

import com.proejct.ClassActionClaim.domain.Notes;
import com.proejct.ClassActionClaim.repository.custom.NotesRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Notes, Long>, NotesRepositoryCustom {
}
