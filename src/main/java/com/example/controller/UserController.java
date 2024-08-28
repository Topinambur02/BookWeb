package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ConfirmRegistrationResponse;
import com.example.dto.SignInDto;
import com.example.dto.SignUpDto;
import com.example.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService service;

    @PostMapping("/signup")
    public SignUpDto signUp(@RequestBody SignUpDto dto) {
        return service.signUp(dto);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody SignInDto dto) {
        return service.signIn(dto);
    }

    @PostMapping("/register/{generated-string}")
    public ConfirmRegistrationResponse confirmRegistration(@PathVariable("generated-string") String generatedString, Authentication authentication) {
        return service.confirmRegistration(generatedString, authentication);
    }

}
