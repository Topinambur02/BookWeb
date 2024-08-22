package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.dto.SignInDto;
import com.example.dto.SignUpDto;
import com.example.service.UserService;

import lombok.RequiredArgsConstructor;

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

}
