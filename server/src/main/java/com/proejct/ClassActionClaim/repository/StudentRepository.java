package com.proejct.ClassActionClaim.repository;

import com.proejct.ClassActionClaim.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
