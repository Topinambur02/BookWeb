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
import com.example.dto.ConfirmRegistrationResponse;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final EmailService emailService;

    private final UserMapper mapper;

    @Autowired
    @Lazy
    private AuthService authService;

    public SignUpDto signUp(SignUpDto dto) {
        final var username = dto.getUsername();
        final var email = dto.getEmail();
        final var isExists = repository.existsByUsername(username);

        if (isExists) {
            throw new IllegalArgumentException(String.format("User with username %s already exists", username));
        }

        final var user = mapper.toUser(dto);
        final var confirmationCode = UUID.randomUUID().toString();
        final var confirmationLink = String.format("http://localhost:8080/api/users/register/%s", confirmationCode);
        final var message = String.format("Hello, %s!\nYour activation link: %s", username, confirmationLink);

        user.setConfirmationCode(confirmationCode);

        emailService.sendMessage(email, "Registration", message);

        final var savedUser = repository.save(user);
        return mapper.toSignUpDto(savedUser);
    }

    public ConfirmRegistrationResponse confirmRegistration(String generatedString, Authentication authentication) {
        final var user = (User) authentication.getPrincipal();
        final var confirmationCode = user.getConfirmationCode();
        final var isCorrect = generatedString.equals(confirmationCode);
        final var response = new ConfirmRegistrationResponse();

        if (!isCorrect) {
            response.setConfirmation(false);
            return response;
        }

        user.setConfirmationCode(null);
        user.setRole(Role.USER);

        repository.save(user);

        response.setConfirmation(true);

        return response;
    }

    public String signIn(SignInDto dto) {
        return authService.signIn(dto);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User not found with login: %s", username)));
    }

}
