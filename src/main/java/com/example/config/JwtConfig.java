package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Configuration
@AllArgsConstructor
@ConfigurationProperties("jwt")
public class JwtConfig {
    
    private String secret;

    private int expirationMs;

}
