package com.khamidgaipov.api.giybat.uz.service;

import com.khamidgaipov.api.giybat.uz.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    @Value("${spring.mail.username}")
    private String fromAccount = "gaipovdev@mail.ru";

    @Value("${server.domain}")
    private String serverDomain;

    @Autowired
    JavaMailSender sender;

    public void sendRegEmail(String email, Long profileId) {
        String subj = "Complete reg.";
        String body = "<!DOCTYPE html>\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/html\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Verification Page</title>\n" +
                "  <style>\n" +
                "      a {\n" +
                "          padding: 10px 30px;\n" +
                "          display: inline-block;\n" +
                "      }\n" +
                "      .button-link {\n" +
                "          text-decoration: none;\n" +
                "          color: white;\n" +
                "          background-color: #00a1ec;\n" +
                "      }\n" +
                "      .button-link:hover {\n" +
                "          background-color: #dd4444;\n" +
                "      }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Complete Registration</h1>\n" +
                "<p>\n" +
                "    Please click to button for complete registration.\n" +
                "</p>\n" +
                "<a href=\"%s/auth/registration/verification/%s\" target=\"_blank\" class=\"button-link\">Click</a>\n" +
                "</body>\n" +
                "</html>";
        body = String.format(body, serverDomain, JwtUtil.encode(profileId));
        sendMimeMessage(email, subj, body);
//        sendEmail(email, subj, body);
    }

    public void sendSimpleEmail(String email, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromAccount);
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(body);

        sender.send(msg);
    }

    public void sendMimeMessage(String email, String subject, String body) {
        try {
            MimeMessage msg = sender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            sender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
