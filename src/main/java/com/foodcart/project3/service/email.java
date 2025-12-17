package com.foodcart.project3.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class email {
private final JavaMailSender javaMailSender;

    public email(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendmails(String to,String sub,String text) throws MessagingException {
        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);

        helper.setFrom("limbadtejas99@gmail.com");
        helper.setTo(to);
        helper.setSubject(sub);
        helper.setText(text);
        javaMailSender.send(message);




    }
}
