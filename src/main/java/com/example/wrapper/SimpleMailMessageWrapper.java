package com.example.wrapper;

import org.springframework.mail.SimpleMailMessage;

public class SimpleMailMessageWrapper extends SimpleMailMessage {
    
    public static class Builder {

        private String to;
        private String subject;
        private String text;

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

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
