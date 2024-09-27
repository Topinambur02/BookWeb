package com.example.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.dto.SignInDto;
import com.example.dto.TokenResponse;
import com.example.security.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager manager;

    public TokenResponse signIn(SignInDto dto) {
        final var username = dto.getUsername();
        final var password = dto.getPassword();

        final var auth = manager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));

        final var jwt = jwtUtils.generateJwtToken(auth);

        final var response = TokenResponse
                .builder()
                .token(jwt)
                .build();

        return response;
    }
}
