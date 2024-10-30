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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final JwtService jwtService;
    private final UserRepository repository;
    private final EmailService emailService;
    private final AuthenticationManager manager;
    private final PooledPBEStringEncryptor encryptor;

    public SignUpDto signUp(SignUpDto dto) {
        final var username = dto.getUsername();
        final var email = dto.getEmail();
        final var confirmationCode = UUID.randomUUID().toString();
        final var isExists = repository.existsByUsername(username);

        if (isExists) {
            throw new IllegalArgumentException(String.format("User with username %s already exists", username));
        }

        final var user = createUser(email, confirmationCode);

        sendEmailMessageDto(username, email, confirmationCode);

        mapper.update(dto, user);

        final var savedUser = repository.save(user);

        return mapper.toSignUpDto(savedUser);
    }

    public ConfirmRegistrationDto confirmRegistration(String generatedString, Authentication authentication) {
        final var user = (User) authentication.getPrincipal();
        final var encryptedConfirmationCode = user.getConfirmationCode();
        final var confirmationCode = encryptor.decrypt(encryptedConfirmationCode);
        final var isCorrect = generatedString.equals(confirmationCode);

        if (!isCorrect) {
            return buildConfirmRegistrationDto(false);
        }

        updateUser(user);

        return buildConfirmRegistrationDto(true);
    }

    public TokenDto signIn(SignInDto dto) {
        final var username = dto.getUsername();
        final var password = dto.getPassword();
        final var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        final var authentication = manager.authenticate(authenticationToken);
        final var jwt = jwtService.generateJwtToken(authentication);

        return TokenDto
                .builder()
                .token(jwt)
                .build();
    }

    private User createUser(String email, String confirmationCode) {
        final var encryptedEmail = encryptor.encrypt(email);
        final var encryptedConfirmationCode = encryptor.encrypt(confirmationCode);

        return User
                .builder()
                .email(encryptedEmail)
                .confirmationCode(encryptedConfirmationCode)
                .build();
    }

    private void sendEmailMessageDto(String username, String email, String confirmationCode) {
        final var confirmationLink = String.format("http://localhost:8080/api/users/register/%s", confirmationCode);
        final var message = String.format("Hello, %s!%nYour activation link: %s", username, confirmationLink);
        final var emailMessageDto = EmailMessageDto
                .builder()
                .to(email)
                .subject("Confirm registration")
                .text(message)
                .build();

        emailService.sendMessage(emailMessageDto);
    }

    private ConfirmRegistrationDto buildConfirmRegistrationDto(boolean isConfirmed) {
        return ConfirmRegistrationDto
                .builder()
                .confirmation(isConfirmed)
                .build();
    }

    private void updateUser(User user) {
        user.setConfirmationCode(null);
        user.setRole(Role.USER);

        repository.save(user);
    }

}
