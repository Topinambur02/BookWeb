package com.example.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.wrapper.SimpleMailMessageWrapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendMessage(String to, String subject, String text) {
        try {
            final var message = SimpleMailMessageWrapper
                    .builder()
                    .to(to)
                    .subject(subject)
                    .text(text)
                    .build();

            emailSender.send(message);
        }

        catch (MailException e) {
            e.printStackTrace();
        }
    }
}
