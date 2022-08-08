package com.proejct.ClassActionClaim.controller;

import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/signup")
    public String signup() {
        return "signUpPage";
    }

    @PostMapping("/signup")
    public UserResponse studentSignUp(@ModelAttribute String username, @ModelAttribute String password, @ModelAttribute String studentEmail) {
        StudentRequestDTO studentRequestDTO = StudentRequestDTO.of(username, password, studentEmail);
        log.info("[POST] /student/signup => " + studentRequestDTO);
        return studentService.addStudent(studentRequestDTO);
    }
}
