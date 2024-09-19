package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ConfirmRegistrationResponse;
import com.example.dto.SignInDto;
import com.example.dto.SignUpDto;
import com.example.dto.TokenResponse;
import com.example.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Авторизация и регистрация пользователя", description = "Методы для авторизации, регистрации и подтверждения регистрации пользователя")
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService service;

    @Operation(summary = "Регистрация пользователя", description = "Получает DTO пользователя и создает нового пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignUpDto.class))),
    })
    @PostMapping("/signup")
    public SignUpDto signUp(@RequestBody SignUpDto dto) {
        return service.signUp(dto);
    }

    @Operation(summary = "Авторизация пользователя", description = "Получает DTO пользователя и авторизует пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь авторизован", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponse.class))),
    })
    @PostMapping("/signin")
    public TokenResponse signIn(@RequestBody SignInDto dto) {
        return service.signIn(dto);
    }

    @Operation(summary = "Подтверждение регистрации пользователя", description = "Получает DTO пользователя и подтверждает регистрацию пользователя")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Пользователь подтвержден", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConfirmRegistrationResponse.class))),
    })
    @PostMapping("/register/{generated-string}")
    public ConfirmRegistrationResponse confirmRegistration(@PathVariable("generated-string") @Parameter(description = "Сгенерированный код подтверждения", example = "akjcsjhdsd21sdjggcsh") String generatedString, Authentication authentication) {
        return service.confirmRegistration(generatedString, authentication);
    }

}
