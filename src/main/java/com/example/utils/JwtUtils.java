package com.example.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationMs}")
    private int expirationMs;

    public String generateJwtToken(Authentication authentication) {
        final var userDetails = (UserDetails) authentication.getPrincipal();
        final var userName = userDetails.getUsername();
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
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    
}
