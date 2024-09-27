package com.example.bookweb.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.dto.SignInDto;
import com.example.security.JwtUtils;
import com.example.service.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private AuthService service;
    @Mock
    private AuthenticationManager manager;

    @Test
    void testSignIn() {
        final var username = "testUser";
        final var password = "testPass";
        final var dto = SignInDto
                .builder()
                .username(username)
                .password(password)
                .build();

        final var authentication = new UsernamePasswordAuthenticationToken(username, password);
        final var expected = "testJwtToken";

        when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn(expected);

        final var actual = service.signIn(dto).getToken();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
