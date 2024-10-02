package com.example.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.wrapper.JavaMailSenderImplWrapper;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailConfig {

    private String username;
    private String password;
    private static final Map<String, String> PROPERTIES = Map.of(
            "mail.transport.protocol", "smtp",
            "mail.smtp.auth", "true",
            "mail.smtp.starttls.enable", "true");

    @Bean
    JavaMailSender javaMailSender() {

        return JavaMailSenderImplWrapper
                .builder()
                .port(587)
                .host("smtp.gmail.com")
                .username(username)
                .password(password)
                .properties(PROPERTIES)
                .build();
    }

}
