package com.example.demo.twoFactorAuthentication;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setSubject(subject);
            helper.setFrom("demo1");
            helper.setTo(to);
            helper.setText(text,true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("ოოო რაღაცა მესიჯის შექმნამ დაუბერა:"+e);
        }
    }


}
