package com.example.wrapper;

import java.util.Map;
import java.util.Properties;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class JavaMailSenderImplWrapper extends JavaMailSenderImpl {

    public static class Builder {
        private String host;
        private int port;
        private String username;
        private String password;
        private Properties properties;

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder properties(Map<String, String> properties) {
            this.properties.putAll(properties);
            return this;
        }

        public JavaMailSenderImplWrapper build() {
            final var mailSender = new JavaMailSenderImplWrapper();
            mailSender.setHost(host);
            mailSender.setPort(port);
            mailSender.setUsername(username);
            mailSender.setPassword(password);
            mailSender.setJavaMailProperties(properties);
            return mailSender;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
