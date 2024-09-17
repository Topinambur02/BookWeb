package com.example.bookweb.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.dto.SignUpDto;
import com.example.encryption.EncryptionUtils;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.service.AuthService;
import com.example.service.EmailService;
import com.example.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserService service;
    @Mock
    private AuthService authService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserRepository repository;
    @Mock
    private Authentication authentication;
    @Mock
    private EncryptionUtils encryptionUtils;

    @Test
    void testSignUpSuccess() {
        final var username = "test";
        final var email = "test";
        final var encryptedEmail = "test";
        final var encryptedConfirmationCode = "test";
        final var dto = SignUpDto
                .builder()
                .username(username)
                .email(email)
                .build();
        final var user = User
                .builder()
                .email(email)
                .confirmationCode(encryptedConfirmationCode)
                .build();
        final var expected = SignUpDto
                .builder()
                .build();

        when(repository.existsByUsername(username)).thenReturn(false);
        lenient().when(encryptionUtils.encrypt(email)).thenReturn(encryptedEmail);
        when(encryptionUtils.encrypt(any(String.class))).thenReturn(encryptedConfirmationCode);
        doNothing().when(mapper).update(dto, user);
        when(repository.save(any(User.class))).thenReturn(user);
        when(mapper.toSignUpDto(user)).thenReturn(expected);

        final var actual = service.signUp(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
        verify(emailService).sendMessage(eq(email), eq("Confirm registration"), any(String.class));
        verify(repository).save(any(User.class));
    }

    @Test
    void testSignUpAlreadyExists() {
        final var username = "test";
        final var dto = SignUpDto
                .builder()
                .username(username)
                .build();

        final var expected = IllegalArgumentException.class;

        when(repository.existsByUsername(username)).thenReturn(true);

        Assertions.assertThatThrownBy(() -> service.signUp(dto)).isInstanceOf(expected);
        verify(repository, never()).save(any(User.class));
        verify(emailService, never()).sendMessage(anyString(), anyString(), anyString());
    }

    @Test
    public void testConfirmRegistrationSuccess() {
        final var generatedString = "test";
        final var encryptedConfirmationCode = "test";
        final var decryptedConfirmationCode = "test";
        final var user = User
                .builder()
                .confirmationCode(encryptedConfirmationCode)
                .build();

        final var expected = Role.USER;

        when(authentication.getPrincipal()).thenReturn(user);
        when(encryptionUtils.decrypt(encryptedConfirmationCode)).thenReturn(decryptedConfirmationCode);

        final var actual = service
                .confirmRegistration(generatedString, authentication)
                .getConfirmation();

        Assertions.assertThat(actual).isTrue();
        Assertions.assertThat(user.getConfirmationCode()).isNull();
        Assertions.assertThat(user.getRole()).isEqualTo(expected);
        verify(repository).save(user);
    }

    @Test
    public void testConfirmRegistrationFailure() {
        final var generatedString = "test";
        final var expected = "test1";
        final var decryptedConfirmationCode = "test1";
        final var user = User
                .builder()
                .confirmationCode(expected)
                .build();

        when(authentication.getPrincipal()).thenReturn(user);
        when(encryptionUtils.decrypt(expected)).thenReturn(decryptedConfirmationCode);

        final var actual = service
                .confirmRegistration(generatedString, authentication)
                .getConfirmation();

        Assertions.assertThat(actual).isFalse();
        Assertions.assertThat(user.getConfirmationCode()).isEqualTo(expected);
        verify(repository, never()).save(user);
    }

}