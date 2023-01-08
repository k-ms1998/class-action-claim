package com.proejct.ClassActionClaim.controller.server;

import com.proejct.ClassActionClaim.dto.RequestBody.EmailAuthRequest;
import com.proejct.ClassActionClaim.dto.RequestBody.EmailLinkRequest;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.service.server.EmailAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    /**
     * Email 전송시
     */
    @PostMapping("/send_auth_code")
    public void sendAuthCode(@RequestBody StudentRequestDTO studentRequestDTO, HttpServletRequest request, HttpServletResponse response) {

        emailAuthService.sendAuthCode(studentRequestDTO, request, response);
    }

    @PostMapping("/authenticate")
    public void authenticateCode(@RequestBody EmailAuthRequest emailAuthData, HttpServletRequest request, HttpServletResponse response) {
        emailAuthService.authenticateAuthCode(emailAuthData, request, response);
    }

    @PostMapping("/authenticate-link")
    public void authenticateLink(@RequestBody EmailLinkRequest emailLinkRequest) {
        log.info("{EmailAuthController} authenticate-link");
        emailAuthService.authenticateLink(emailLinkRequest);
    }

}
