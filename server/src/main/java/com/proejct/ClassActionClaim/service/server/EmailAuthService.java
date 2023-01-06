package com.proejct.ClassActionClaim.service.server;

import com.proejct.ClassActionClaim.dto.RequestBody.EmailAuthRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.EmailAuthResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.jwt.properties.JwtProperties;
import com.proejct.ClassActionClaim.jwt.properties.JwtUtils;
import com.proejct.ClassActionClaim.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final StudentService studentService;
    private final JavaMailSender mailSender;
    private static final String SENDER_EMAIL = "k_ms1998@naver.com";
    private static final String EMAIL_SUBJECT = "Class-Action-Claim";

    /**
     * Reference:
     * https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/mail.html
     *
     */
    public void sendAuthCode(StudentRequestDTO studentRequestDTO, HttpServletRequest request, HttpServletResponse response){
        log.info("[sendAuthCode] studentRequestDTO = " + studentRequestDTO);
        String toEmail = studentRequestDTO.getEmail();
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper msgHelper = new MimeMessageHelper(message);

            msgHelper.setFrom(SENDER_EMAIL);
            msgHelper.setTo(toEmail);

            Random random = new Random();
            String authCode = String.valueOf(random.nextInt(999999));
            msgHelper.setSubject(EMAIL_SUBJECT);
            msgHelper.setText(createMessage(authCode));

            mailSender.send(message);


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

    public ToClientResponse authenticateAuthCode(EmailAuthRequest emailAuthData, HttpServletRequest request, HttpServletResponse response) {
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
            log.info("!! Couldn't Retrieve Token !!");
            e.printStackTrace();
            return new ToClientResponse("Unsuccessful", 0, 400, null);
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
                    /**
                     * 인증번호 일치
                     */
                    log.info("{ email = " + tokenEmail + ", code = " + tokenCode + " }");

                    String reqName = emailAuthData.getName();
                    String reqPassword = emailAuthData.getPassword();
                    StudentRequestDTO studentRequestDTO = new StudentRequestDTO(reqName, reqPassword, reqEmail);
                    studentService.addStudent(studentRequestDTO);
                    return new ToClientResponse("Sign Up Successful", 0, 200, null);
                } else {
                    log.info("Incorrect Code");
                    return new ToClientResponse("Unsuccessful", 0, 400, null);
                }
            }
        }
        else{
            log.info("!! Token is NULL !!");
            return new ToClientResponse("Unsuccessful", 0, 400, null);
        }

        return new ToClientResponse("Unsuccessful", 0, 400, null);
    }

    private String createMessage(String authCode) {
        return "Please input the following code: \n"
                +authCode;
    }

}
