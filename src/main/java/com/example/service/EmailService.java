package com.example.service;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    
    private final JavaMailSender emailSender;

    public void sendMessage(String to, String subject, String text) {
        try {
            final var message = new SimpleMailMessage();

            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
    
            emailSender.send(message);
        }

        catch (MailException e) {
            e.printStackTrace();
        }
    }
}
