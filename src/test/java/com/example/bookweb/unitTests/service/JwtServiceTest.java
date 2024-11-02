package com.example.bookweb.unitTests.service;

import static org.mockito.Mockito.when;

import java.util.Base64;
import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.config.JwtConfig;
import com.example.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private JwtConfig config;
    @InjectMocks
    private JwtService service;
    @Mock
    private UserDetails userDetails;
    @Mock
    private Authentication authentication;

    @Test
    void testGenerateJwtToken() {
        final var username = "testUser";
        final var expirationMs = 3600000;
        final var secret = Base64
                .getEncoder()
                .encodeToString("kjfdhgjkdfhjlfhgdjsfhgdsjfhgdfjghdjfhbvdjfhbv".getBytes());

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);
        when(config.getExpirationMs()).thenReturn(expirationMs);
        when(config.getSecret()).thenReturn(secret);

        final var token = service.generateJwtToken(authentication);

        Assertions.assertThat(token).isNotEmpty().isNotNull();
    }

    @Test
    void testGetUserName() {
        final var username = "testUser";
        final var secret = Base64
                .getEncoder()
                .encodeToString("kjfdhgjkdfhjlfhgdjsfhgdsjfhgdfjghdjfhbvdjfhbv".getBytes());
        final var token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .compact();

        when(config.getSecret()).thenReturn(secret);

        final var extractedUsername = service.getUserName(token);

        Assertions.assertThat(username).isEqualTo(extractedUsername);
    }

    @Test
    void testValidateJwtTokenSuccess() {
        final var secret = Base64
                .getEncoder()
                .encodeToString("kjfdhgjkdfhjlfhgdjsfhgdsjfhgdfjghdjfhbvdjfhbv".getBytes());
        final var token = Jwts
                .builder()
                .subject("testUser")
                .issuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .compact();

        when(config.getSecret()).thenReturn(secret);

        final var isValid = service.validateJwtToken(token);

        Assertions.assertThat(isValid).isTrue();
    }

    @Test
    void testValidateJwtTokenFailure() {
        final var secret = Base64
                .getEncoder()
                .encodeToString("kjfdhgjkdfhjlfhgdjsfhgdsjfhgdfjghdjfhbvdjfhbv".getBytes());
        final var invalidToken = "invalidToken";

        when(config.getSecret()).thenReturn(secret);

        final var isValid = service.validateJwtToken(invalidToken);

        Assertions.assertThat(isValid).isFalse();
    }

}
