package com.example.encryption;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.example.wrapper.SimpleStringPBEConfigWrapper;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "encrypt")
public class EncryptionUtils {

    private String secretKey;
    private PooledPBEStringEncryptor encryptor;

    public EncryptionUtils() {
        encryptor = new PooledPBEStringEncryptor();
    }

    @PostConstruct
    public void init() {
        final var config = SimpleStringPBEConfigWrapper
                .builder()
                .password(secretKey)
                .poolSize("1")
                .build();

        encryptor.setConfig(config);
    }

    public String encrypt(String text) {
        return encryptor.encrypt(text);
    }

    public String decrypt(String text) {
        return encryptor.decrypt(text);
    }

}