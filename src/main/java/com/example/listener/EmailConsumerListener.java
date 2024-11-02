package com.example.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.dto.kafka.KafkaEmailMessageDto;
import com.example.mapper.EmailMessageMapper;
import com.example.service.EmailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailConsumerListener {
    
    private final ObjectMapper objectMapper;
    private final EmailMessageMapper mapper;
    private final EmailService emailService;

    @KafkaListener(topics = "email-message", groupId = "email-group")
    public void consume(String message) throws JsonProcessingException {
        final var dto = objectMapper.readValue(message, KafkaEmailMessageDto.class);
        final var emailMessageDto = mapper.toEmailMessageDto(dto);

        emailService.sendMessage(emailMessageDto);
    }

}
