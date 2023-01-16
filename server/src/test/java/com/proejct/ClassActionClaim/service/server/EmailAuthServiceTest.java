package com.proejct.ClassActionClaim.service.server;

import com.proejct.ClassActionClaim.service.server.EmailAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class EmailAuthServiceTest {

    @Autowired
    private EmailAuthService emailAuthService;

    @Test
    void givenEmail_whenSendingAuthCode_thenSuccess() throws MessagingException {
        // Given
        String email = "kms1998@sju.ac.kr";
        // When
//        emailAuthService.sendAuthCode();

        // Then

    }

}