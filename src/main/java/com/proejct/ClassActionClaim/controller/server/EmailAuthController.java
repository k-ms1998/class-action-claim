package com.proejct.ClassActionClaim.controller.server;

import com.proejct.ClassActionClaim.service.server.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    /**
     * Email 전송시
     */
    @GetMapping("/test/mail")
    public void sendAuthCode() {
        emailAuthService.sendAuthCode("kms1998@sju.ac.kr");
    }
}
