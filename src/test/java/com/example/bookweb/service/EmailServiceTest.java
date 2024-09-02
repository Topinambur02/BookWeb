package com.example.bookweb.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.service.EmailService;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService service;
    
    @Test
    void testSendEmail() {
        final var to = "test@example.com";
        final var subject = "Test Subject";
        final var text = "Test message";
        final var expectedMessage = new SimpleMailMessage();

        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(text);

        service.sendMessage(to, subject, text);

        verify(javaMailSender).send(expectedMessage);
    }

    @Test
    public void testSendMessage_withMailException() {
        final var to = "test@example.com";
        final var subject = "Test Subject";
        final var text = "Test message";

        doThrow(new MailException("Mail sending failed") {}).when(javaMailSender).send(any(SimpleMailMessage.class));

        service.sendMessage(to, subject, text);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }

}
