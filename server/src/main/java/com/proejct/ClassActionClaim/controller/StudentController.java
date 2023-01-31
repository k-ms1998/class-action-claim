package com.proejct.ClassActionClaim.controller;

import com.proejct.ClassActionClaim.dto.RequestBody.StudentLoginRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.StudentLoginResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/signup")
    public String signup() {
        log.info("[GET] /student/signup");
        return "signUpPage";
    }

    @PostMapping("/join")
    public ToClientResponse<UserResponse> studentSignUp(@RequestBody StudentRequestDTO studentRequestDTO) {
        UserResponse userResponse = studentService.addStudent(studentRequestDTO);

        return new ToClientResponse<>("Student Joined.", 1, HttpStatus.OK, userResponse);
    }

    @PostMapping("/login")
    public ToClientResponse<StudentLoginResponse> studentLogin(@RequestBody StudentLoginRequest studentLoginRequest) {
        StudentLoginResponse loginResponse = studentService.login(studentLoginRequest);

        return new ToClientResponse<>("Student Logged In.", 1, HttpStatus.OK, loginResponse);
    }
}
