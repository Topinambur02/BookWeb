package com.example.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.dto.EmailMessageDto;
import com.example.mapper.EmailMessageMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailConsumerService {
    
    private final ObjectMapper objectMapper;
    private final EmailMessageMapper mapper;
    private final JavaMailSender emailSender;

    @KafkaListener(topics = "email-message", groupId = "email-group")
    public void consume(String message) throws Exception {
        final var dto = objectMapper.readValue(message, EmailMessageDto.class);

        final var emailMessage = mapper.toSimpleMailMessage(dto);

        emailSender.send(emailMessage);
    }

}