package com.proejct.ClassActionClaim.service.server;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final JavaMailSender mailSender;

    public static final String EMAIL_SUBJECT = "TEST EMAIL C.A.C";
    public static final String SENDER_EMAIL = "k_ms1998@naver.com";

    /**
     * Reference:
     * https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/mail.html
     *
     * @param toEmail
     */
    public void sendAuthCode(String toEmail){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper msgHelper = new MimeMessageHelper(message);

            msgHelper.setFrom(SENDER_EMAIL);
            msgHelper.setTo(toEmail);
            msgHelper.setSubject(EMAIL_SUBJECT);
            msgHelper.setText("This is a test.");
            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void authenticateAuthCode(Integer userSentAuthCode) {

    }



}
