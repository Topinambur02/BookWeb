package com.example.encryption;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "encrypt")
@Data
public class EncryptionUtils {

    private String secretKey;

    private PooledPBEStringEncryptor encryptor;

    public EncryptionUtils() {
        encryptor = new PooledPBEStringEncryptor();
    }

    @PostConstruct
    public void init() {
        final var config = new SimpleStringPBEConfig();

        config.setPassword(secretKey);
        config.setPoolSize("1");

        encryptor.setConfig(config);
    }

    public String encrypt(String text) {
        return encryptor.encrypt(text);
    }

    public String decrypt(String text) {
        return encryptor.decrypt(text);
    }

}