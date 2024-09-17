package com.example.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.SignInDto;
import com.example.dto.SignUpDto;
import com.example.dto.TokenResponse;
import com.example.encryption.EncryptionUtils;
import com.example.dto.ConfirmRegistrationResponse;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserMapper mapper;
    @Lazy
    @Autowired
    private AuthService authService;
    private final UserRepository repository;
    private final EmailService emailService;
    private final EncryptionUtils encryptionUtils;

    public SignUpDto signUp(SignUpDto dto) {
        final var username = dto.getUsername();
        final var email = dto.getEmail();

        final var isExists = repository.existsByUsername(username);

        if (isExists) {
            throw new IllegalArgumentException(String.format("User with username %s already exists", username));
        }

        final var confirmationCode = UUID.randomUUID().toString();
        final var confirmationLink = String.format("http://localhost:8080/api/users/register/%s", confirmationCode);
        final var message = String.format("Hello, %s!\nYour activation link: %s", username, confirmationLink);

        final var encryptedEmail = encryptionUtils.encrypt(email);
        final var encryptedConfirmationCode = encryptionUtils.encrypt(confirmationCode);

        final var user = User
                .builder()
                .email(encryptedEmail)
                .confirmationCode(encryptedConfirmationCode)
                .build();

        mapper.update(dto, user);

        emailService.sendMessage(email, "Confirm registration", message);

        final var savedUser = repository.save(user);

        return mapper.toSignUpDto(savedUser);
    }

    public ConfirmRegistrationResponse confirmRegistration(String generatedString, Authentication authentication) {
        final var user = (User) authentication.getPrincipal();
        final var encryptedConfirmationCode = user.getConfirmationCode();
        final var confirmationCode = encryptionUtils.decrypt(encryptedConfirmationCode);
        final var isCorrect = generatedString.equals(confirmationCode);

        if (!isCorrect) {
            return ConfirmRegistrationResponse
                    .builder()
                    .confirmation(false)
                    .build();
        }

        user.setConfirmationCode(null);
        user.setRole(Role.USER);

        repository.save(user);

        return ConfirmRegistrationResponse
                .builder()
                .confirmation(true)
                .build();
    }

    public TokenResponse signIn(SignInDto dto) {
        return authService.signIn(dto);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User not found with login: %s", username)));
    }

}
