package com.mexcorgo.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender mailSender;

    // ✅ New Method to Send Bulk Emails
    public void sendBulkEmail(List<String> recipients, String subject, String content) {
        for (String email : recipients) {
            sendEmail(email, subject, content);
        }
    }

    public void sendEmail(String to, String subject, String content) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        message.setFrom("payalpaunikar1001@gmail.com");  // Set sender email
//
//        mailSender.send(message);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true = enables HTML
            helper.setFrom("mexcargotransport@gmail.com");

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send email to " + to);
            e.printStackTrace();
        }
    }
}
