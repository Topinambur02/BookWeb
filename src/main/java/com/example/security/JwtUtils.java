package com.example.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtUtils {

    private String secret;

    private int expirationMs;

    public String generateJwtToken(Authentication auth) {
        final var userDetails = (UserDetails) auth.getPrincipal();
        final var date = new Date((new Date()).getTime() + expirationMs);

        return Jwts
                .builder()
                .subject(userDetails.getUsername())
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
