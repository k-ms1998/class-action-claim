package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void givenStudentDto_whenCallingAddStudent_thenReturnSuccessfulMessage() throws Exception{
        // Given
        StudentRequestDTO studentRequestDTO = StudentRequestDTO.of("testStudent", "123", "test@sejong.com");

        // When
        UserResponse userResponse = studentService.addStudent(studentRequestDTO);

        // Then
        String message = userResponse.getMessage();
        Assertions.assertThat(message).isEqualTo("Student Sign Up Successful");

    }
}