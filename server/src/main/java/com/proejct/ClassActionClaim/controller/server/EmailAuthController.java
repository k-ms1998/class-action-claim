package com.proejct.ClassActionClaim.controller.server;

import com.proejct.ClassActionClaim.dto.RequestBody.EmailAuthRequest;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.service.server.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    /**
     * Email 전송시
     */
    @PostMapping("/mail/send_auth_code")
    public void sendAuthCode(@RequestBody StudentRequestDTO studentRequestDTO, HttpServletRequest request, HttpServletResponse response) {

        emailAuthService.sendAuthCode(studentRequestDTO, request, response);
    }

    @PostMapping("/mail/authenticate")
    public void authenticateCode(@RequestBody EmailAuthRequest emailAuthData, HttpServletRequest request, HttpServletResponse response) {
        emailAuthService.authenticateAuthCode(emailAuthData, request, response);
    }
}
