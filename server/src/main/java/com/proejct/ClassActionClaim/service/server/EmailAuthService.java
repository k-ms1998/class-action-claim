package com.proejct.ClassActionClaim.service.server;

import com.proejct.ClassActionClaim.domain.Student;
import com.proejct.ClassActionClaim.dto.RequestBody.EmailAuthRequest;
import com.proejct.ClassActionClaim.dto.RequestBody.EmailLinkRequest;
import com.proejct.ClassActionClaim.dto.ResponseBody.EmailAuthResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.ToClientResponse;
import com.proejct.ClassActionClaim.dto.ResponseBody.UserResponse;
import com.proejct.ClassActionClaim.dto.StudentRequestDTO;
import com.proejct.ClassActionClaim.jwt.properties.JwtProperties;
import com.proejct.ClassActionClaim.jwt.properties.JwtUtils;
import com.proejct.ClassActionClaim.repository.StudentRepository;
import com.proejct.ClassActionClaim.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailAuthService {

//    private final StudentService studentService;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final StudentRepository studentRepository;

    private static final String SENDER_EMAIL = "k_ms1998@naver.com";
    private static final String EMAIL_SUBJECT = "Class-Action-Claim";
    private static final String VERIFICATION_BASE_LINK = "http://localhost:8080/mail";

    /**
     * Reference:
     * https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/mail.html
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
            return new ToClientResponse("Unsuccessful", 0, HttpStatus.BAD_REQUEST, null);
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
//                    studentService.addStudent(studentRequestDTO);
                    return new ToClientResponse("Sign Up Successful", 0, HttpStatus.OK, null);
                } else {
                    log.info("Incorrect Code");
                    return new ToClientResponse("Unsuccessful", 0, HttpStatus.BAD_REQUEST, null);
                }
            }
        }
        else{
            log.info("!! Token is NULL !!");
            return new ToClientResponse("Unsuccessful", 0, HttpStatus.BAD_REQUEST, null);
        }

        return new ToClientResponse("Unsuccessful", 0, HttpStatus.BAD_REQUEST, null);
    }

    /**
     * 회원가입 인증 방법:
     * 1. 회원가입 정보를 입력
     * 2. 입력된 이메일로 인증 링크 발송
     * 3. 사용자가 인증 링크 클릭시 인증 완료 및 회원가입 완료
     */
    public void sendVerificationLink(String email, String uuid) { // TODO: 인증 링크를 보낼 html 페이지 생성 및 이메일의 본문으로 보내기
        String verificationLink = new StringBuilder()
                .append(VERIFICATION_BASE_LINK).append("/authenticate-link?").append(uuid)
                .toString();


        final MimeMessagePreparator preparator = mimeMessage -> {
            final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setFrom(SENDER_EMAIL);
            helper.setTo(email);
            helper.setSubject(EMAIL_SUBJECT);
            helper.setText(verificationLink);
        };

        mailSender.send(preparator);
    }

    public  void authenticateLink(EmailLinkRequest emailLinkRequest) { //TODO: 각 인증 실패 상황에 대해서 적절한 Response 생성 및 반환
        String email = emailLinkRequest.getEmail();
        String uuid = emailLinkRequest.getUuid();
        LocalDateTime requestTime = emailLinkRequest.getRequestTime();
        LocalDateTime now = LocalDateTime.now();

        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            log.info("[Verification Failed] Student Doesn't Exists.");
            return;
        }
        if(student.isAuthenticated()){
            log.info("[Verification Failed] Student Already Verified.");
            return;
        }
        if (now.isAfter(requestTime.plusDays(1))) {
            log.info("[Verification Failed] Student Verification Timeout.");
            return;
        }
        if(!student.getUuid().equals(uuid)){
            log.info("[Verification Failed] Invalid Request");
            return;
        }

        student.updateAuthenticated(true);
        studentRepository.save(student);
        log.info("[Verification Success] Student Verified");
        return;
    }

    private String createMessage(String authCode) {
        return "Please input the following code: \n"
                +authCode;
    }
}
