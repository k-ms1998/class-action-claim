package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public UserResponse addStudent(StudentRequestDTO studentRequestDTO) {
        String name = studentRequestDTO.getName();
        String password = studentRequestDTO.getPassword();
        String studentEmail = studentRequestDTO.getStudentEmail();
        String authority = "ROLE_STUDENT";

        Student student = Student.of(name, password, studentEmail);
        log.info("Student Created");
        Student saved = studentRepository.save(student);
        log.info("Student Saved");

        return new UserResponse("Student Sign Up Successful");
    }
}
