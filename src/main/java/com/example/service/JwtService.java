package com.example.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.config.JwtConfig;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig config;

    public String generateJwtToken(Authentication authentication) {
        final var userDetails = (UserDetails) authentication.getPrincipal();
        final var userName = userDetails.getUsername();
        final var expirationMs = config.getExpirationMs();
        final var fastTime = new Date().getTime() + expirationMs;
        final var date = new Date(fastTime);

        return Jwts
                .builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(date)
                .signWith(key())
                .compact();
    }

    public String getUserName(String token) {
        return Jwts
                .parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts
                    .parser()
                    .verifyWith(key())
                    .build()
                    .parse(authToken);

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private SecretKey key() {
        final var secret = config.getSecret();
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    
}
