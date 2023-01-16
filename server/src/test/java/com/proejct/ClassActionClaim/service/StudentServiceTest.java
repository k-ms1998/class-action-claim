package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import com.proejct.ClassActionClaim.service.server.EmailAuthService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private EmailAuthService emailAuthService;

    @Test
    void givenStudentDto_whenCallingAddStudent_thenReturnSuccessfulMessage() throws Exception {
        // Given
        String email = "student@sju.ac.kr";
        String uuid = UUID.randomUUID().toString();
        StudentRequestDTO studentRequestDTO = createStudentRequestDto(email);
        given(studentRepository.findByEmail(email)).willReturn(null);

        // When
        UserResponse response = studentService.addStudent(studentRequestDTO);

        // Then
        Assertions.assertThat(response.getMessage()).isEqualTo("Verification Link Sent.");
        then(studentRepository).should().findByEmail(email);
    }

    private static StudentRequestDTO createStudentRequestDto(String email) {
        return StudentRequestDTO.of(
                "kms",
                "password",
                email
        );
    }
}