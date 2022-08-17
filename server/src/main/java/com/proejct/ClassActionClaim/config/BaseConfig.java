package com.proejct.ClassActionClaim.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@Configuration
public class BaseConfig {

    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
