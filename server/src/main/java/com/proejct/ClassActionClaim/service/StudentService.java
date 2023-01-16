package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import com.proejct.ClassActionClaim.service.server.EmailAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;
    private final EmailAuthService emailAuthService;

    @Transactional
    public UserResponse addStudent(StudentRequestDTO studentRequestDTO) {
        String name = studentRequestDTO.getName();
        String password = studentRequestDTO.getPassword();
        String email = studentRequestDTO.getEmail();
        boolean authenticated = false;
        
        /**
         * 1. 중복된 email 이 존재하는지 확인
         */
        String uuid;
        Student byEmail = studentRepository.findByEmail(email);
        if(byEmail == null){
            /**
             *  2. 중복된 email 이 존재하지 않으면 엔티티 저장
             */
            uuid = UUID.randomUUID().toString();
            studentRepository.save(Student.of(uuid, name, password, email, authenticated));
        }else{
            uuid = byEmail.getUuid();
        }
        /**
         * 3. 입력한 email로 링크 발송
         */
        emailAuthService.sendVerificationLink(email, uuid);

        return new UserResponse("Verification Link Sent.");
    }
}
