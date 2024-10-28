package com.example.bookweb.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.dto.kafka.KafkaEmailMessageDto;
import com.example.mapper.EmailMessageMapper;
import com.example.service.EmailConsumerService;
import com.example.wrapper.SimpleMailMessageWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class EmailConsumerServiceTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EmailMessageMapper mapper;
    @Mock
    private JavaMailSender emailSender;
    @InjectMocks
    private EmailConsumerService emailConsumerService;

    @Test
    void testConsume() throws Exception {
        final var kafkaMessage = "{\"to\": [\"test\"], \"text\": \"Test Text\"}";
        final var dto = KafkaEmailMessageDto
                .builder()
                .to("test")
                .text("Test Text")
                .build();
        final var simpleMailMessage = SimpleMailMessageWrapper
                .builder()
                .to("test")
                .text("Test Text")
                .build();

        when(objectMapper.readValue(kafkaMessage, KafkaEmailMessageDto.class)).thenReturn(dto);
        when(mapper.toSimpleMailMessage(dto)).thenReturn(simpleMailMessage);

        emailConsumerService.consume(kafkaMessage);

        verify(objectMapper, times(1)).readValue(kafkaMessage, KafkaEmailMessageDto.class);
        verify(mapper, times(1)).toSimpleMailMessage(dto);
        verify(emailSender, times(1)).send(simpleMailMessage);
    }

}
