package com.proejct.ClassActionClaim.repository;

import com.proejct.ClassActionClaim.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
