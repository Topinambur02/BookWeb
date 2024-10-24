package com.example.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.dto.rest.EmailMessageDto;
import com.example.mapper.EmailMessageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailMessageMapper mapper;
    private final JavaMailSender emailSender;

    public void sendMessage(EmailMessageDto message) {
        try {
            final var simpleMailMessage = mapper.toSimpleMailMessage(message);

            emailSender.send(simpleMailMessage);
        }
        catch (MailException e) {
            e.printStackTrace();
        }
    }
    
}
