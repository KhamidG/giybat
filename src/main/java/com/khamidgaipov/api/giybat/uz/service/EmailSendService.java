package com.khamidgaipov.api.giybat.uz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    @Value("${spring.mail.username}")
    private String fromAccount = "gaipovdev@mail.ru";

    @Autowired
    JavaMailSender sender;

    public void sendRegEmail(String email, Long profileId) {
        String subj = "Complete reg.";
        String body = "Reg email. Please click to LINK: http://localhost:8080/auth/registration/verification/" + profileId;
        sendEmail(email, subj, body);
    }

    public void sendEmail(String email, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(body);

        sender.send(msg);
    }
}
