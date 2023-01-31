package com.proejct.ClassActionClaim.service;

import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.StudentLoginRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.StudentLoginResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.exception.ClassActionClaimException;
import com.proejct.ClassActionClaim.exception.ErrorCode;
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
            if(byEmail.isAuthenticated()){
                throw new ClassActionClaimException(ErrorCode.DUPLICATE_USER, "");
            }
            uuid = byEmail.getUuid();
        }
        /**
         * 3. 입력한 email로 링크 발송
         */
        emailAuthService.sendVerificationLink(email, uuid);

        return new UserResponse("Verification Link Sent.");
    }

    public StudentLoginResponse login(StudentLoginRequest studentLoginRequest) {
        String email = studentLoginRequest.getEmail();
        String password = studentLoginRequest.getPassword();

        Student byEmail = studentRepository.findByEmail(email);
        if (byEmail == null) {
            log.info("[StudentService] Student Doesn't Exist.");
            throw new ClassActionClaimException(ErrorCode.INVALID_USER, String.format("%s doesn't exist.", email));
        }
        if(!byEmail.isAuthenticated()){
            log.info("[StudentService] Student Unverified.");
            throw new ClassActionClaimException(ErrorCode.UNVERIFIED_USER, String.format("%s is unverified.", email));
        }
        if (!password.equals(byEmail.getPassword())) {
            log.info("[StudentService] Incorrect Password.");
            throw new ClassActionClaimException(ErrorCode.INCORRECT_PASSWORD, "");
        }

        log.info("[StudentService] Student Login Success.");
        return StudentLoginResponse.of(byEmail.getUuid(), byEmail.getEmail(), "Login Success");
    }
}
