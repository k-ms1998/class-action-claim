package com.proejct.ClassActionClaim.service.server;

import com.proejct.ClassActionClaim.dto.RequestBody.EmailAuthRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.EmailAuthResponse;
import com.proejct.ClassActionClaim.jwt.properties.JwtProperties;
import com.proejct.ClassActionClaim.jwt.properties.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Slf4j
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
    public void sendAuthCode(String toEmail, HttpServletRequest request, HttpServletResponse response){
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper msgHelper = new MimeMessageHelper(message);

            msgHelper.setFrom(SENDER_EMAIL);
            msgHelper.setTo(toEmail);
            msgHelper.setSubject(EMAIL_SUBJECT);

            msgHelper.setText("This is a test.");
            mailSender.send(message);

            String authCode = "12345";
            EmailAuthResponse emailAuthResponse = new EmailAuthResponse(toEmail, authCode);

            String token = JwtUtils.createToken(emailAuthResponse.toString());
            Cookie cookie = new Cookie(JwtProperties.SIGNUP_AUTH_COOKIE_NAME, token);
            cookie.setMaxAge(JwtProperties.SIGNUP_AUTH_EXPIRATION_TIME);
            cookie.setPath("/");

            response.addCookie(cookie);
            response.sendRedirect("/");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void authenticateAuthCode(EmailAuthRequest emailAuthData, HttpServletRequest request, HttpServletResponse response) {
        String reqEmail = emailAuthData.getEmail();
        String reqCode = emailAuthData.getCode();

        String token = null;
        try {
            token = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals(JwtProperties.SIGNUP_AUTH_COOKIE_NAME))
                    .findFirst()
                    .map(c -> c.getValue())
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (token != null) {
            String subject = JwtUtils.getSubjectFromToken(token);
            /**
             * Subject == 'email:code'
             */
            if (subject != null) {
                String[] subjectSplit = subject.split(":");
                String tokenEmail = subjectSplit[0];
                String tokenCode = subjectSplit[1];

                if (tokenEmail.equals(reqEmail) && tokenCode.equals(reqCode)) {
                    log.info("{ email = " + tokenEmail + ", code = " + tokenCode + " }");
                } else {
                    log.info("Incorrect Code");
                }
            }
        }



    }

}
