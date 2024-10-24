package com.example.service;

import java.util.UUID;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.dto.rest.ConfirmRegistrationDto;
import com.example.dto.rest.EmailMessageDto;
import com.example.dto.rest.SignInDto;
import com.example.dto.rest.SignUpDto;
import com.example.dto.rest.TokenDto;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.utils.JwtUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final JwtUtils jwtUtils;
    private final UserRepository repository;
    private final EmailService emailService;
    private final AuthenticationManager manager;
    private final PooledPBEStringEncryptor encryptor;

    public SignUpDto signUp(SignUpDto dto) {
        final var username = dto.getUsername();
        final var email = dto.getEmail();
        final var isExists = repository.existsByUsername(username);

        if (isExists) {
            throw new IllegalArgumentException(String.format("User with username %s already exists", username));
        }

        final var confirmationCode = UUID.randomUUID().toString();
        final var confirmationLink = String.format("http://localhost:8080/api/users/register/%s", confirmationCode);
        final var message = String.format("Hello, %s!%nYour activation link: %s", username, confirmationLink);
        final var encryptedEmail = encryptor.encrypt(email);
        final var encryptedConfirmationCode = encryptor.encrypt(confirmationCode);
        final var user = User
                .builder()
                .email(encryptedEmail)
                .confirmationCode(encryptedConfirmationCode)
                .build();
        final var emailMessageDto = EmailMessageDto
                .builder()
                .to(email)
                .subject("Confirm registration")
                .text(message)
                .build();

        mapper.update(dto, user);

        emailService.sendMessage(emailMessageDto);

        final var savedUser = repository.save(user);

        return mapper.toSignUpDto(savedUser);
    }

    public ConfirmRegistrationDto confirmRegistration(String generatedString, Authentication authentication) {
        final var user = (User) authentication.getPrincipal();
        final var encryptedConfirmationCode = user.getConfirmationCode();
        final var confirmationCode = encryptor.decrypt(encryptedConfirmationCode);
        final var isCorrect = generatedString.equals(confirmationCode);

        if (!isCorrect) {
            return ConfirmRegistrationDto
                    .builder()
                    .confirmation(false)
                    .build();
        }

        user.setConfirmationCode(null);
        user.setRole(Role.USER);

        repository.save(user);

        return ConfirmRegistrationDto
                .builder()
                .confirmation(true)
                .build();
    }

    public TokenDto signIn(SignInDto dto) {
        final var username = dto.getUsername();
        final var password = dto.getPassword();
        final var auth = manager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        final var jwt = jwtUtils.generateJwtToken(auth);

        return TokenDto
                .builder()
                .token(jwt)
                .build();
    }

}
