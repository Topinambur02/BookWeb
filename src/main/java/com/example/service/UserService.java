package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.SignInDto;
import com.example.dto.SignUpDto;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    private final UserMapper mapper;

    @Autowired
    private AuthService authService;

    public SignUpDto signUp(SignUpDto dto) {
        final var username = dto.getUsername();

        final var isExists = repository.existsByUsername(username);

        if (isExists) {
            throw new IllegalArgumentException(String.format("User with username %s already exists", username));
        }

        final var user = mapper.toUser(dto);
        final var savedUser = repository.save(user);

        return mapper.toSignUpDto(savedUser);
    }

    public String signIn(SignInDto dto){
        return authService.signIn(dto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = repository.findByUsername(username).orElseThrow(() -> 
                new UsernameNotFoundException(String.format("User not found with login: %s", username)));

        final var springUser = user.toDetails();

        return springUser;
    }

}
