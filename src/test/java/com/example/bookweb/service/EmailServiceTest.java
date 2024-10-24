package com.example.bookweb.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.dto.rest.EmailMessageDto;
import com.example.mapper.EmailMessageMapper;
import com.example.service.EmailService;
import com.example.wrapper.SimpleMailMessageWrapper;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService service;
    @Mock
    private EmailMessageMapper mapper;
    @Mock
    private JavaMailSender javaMailSender;

    @Test
    void testSendEmail() {
        final var to = "test@example.com";
        final var subject = "Test Subject";
        final var text = "Test message";
        final var dto = EmailMessageDto
                .builder()
                .to(to)
                .subject(subject)
                .text(text)
                .build();
        final var expectedMessage = SimpleMailMessageWrapper
                .builder()
                .to(to)
                .subject(subject)
                .text(text)
                .build();

        when(mapper.toSimpleMailMessage(dto)).thenReturn(expectedMessage);

        service.sendMessage(dto);

        verify(javaMailSender).send(expectedMessage);
    }

    @Test
    void testSendMessage_withMailException() {
        final var to = "test@example.com";
        final var subject = "Test Subject";
        final var text = "Test message";
        final var dto = EmailMessageDto
                .builder()
                .to(to)
                .subject(subject)
                .text(text)
                .build();
        final var mailException = new MailException("Mail sending failed") {};
        final var simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        when(mapper.toSimpleMailMessage(dto)).thenReturn(simpleMailMessage);
        doThrow(mailException).when(javaMailSender).send(any(SimpleMailMessage.class));

        service.sendMessage(dto);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

}
