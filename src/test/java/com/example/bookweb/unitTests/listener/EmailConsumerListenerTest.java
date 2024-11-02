package com.example.bookweb.unitTests.listener;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dto.kafka.KafkaEmailMessageDto;
import com.example.dto.rest.EmailMessageDto;
import com.example.listener.EmailConsumerListener;
import com.example.mapper.EmailMessageMapper;
import com.example.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class EmailConsumerListenerTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EmailMessageMapper mapper;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private EmailConsumerListener emailConsumerListener;

    @Test
    void testConsume() throws Exception {
        final var kafkaMessage = "{\"to\": [\"test\"], \"text\": \"Test Text\"}";
        final var dto = KafkaEmailMessageDto
                .builder()
                .to("test")
                .text("Test Text")
                .build();
        final var emailMessageDto = EmailMessageDto
                .builder()
                .to("test")
                .text("Test Text")
                .build();

        when(objectMapper.readValue(kafkaMessage, KafkaEmailMessageDto.class)).thenReturn(dto);
        when(mapper.toEmailMessageDto(dto)).thenReturn(emailMessageDto);
        doNothing().when(emailService).sendMessage(emailMessageDto);

        emailConsumerListener.consume(kafkaMessage);

        verify(objectMapper, times(1)).readValue(kafkaMessage, KafkaEmailMessageDto.class);
        verify(mapper, times(1)).toEmailMessageDto(dto);
        verify(emailService, times(1)).sendMessage(emailMessageDto);
    }

}
