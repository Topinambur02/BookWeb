package com.example.wrapper;

import org.springframework.mail.SimpleMailMessage;

import lombok.Data;
import lombok.experimental.Accessors;

public class SimpleMailMessageWrapper extends SimpleMailMessage {
    
    @Data
    @Accessors(fluent = true, chain = true)
    public static class Builder {

        private String to;
        private String subject;
        private String text;

        public SimpleMailMessage build() {
            final var message = new SimpleMailMessage();

            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            
            return message;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
