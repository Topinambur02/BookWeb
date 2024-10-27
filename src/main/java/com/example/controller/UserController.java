package com.example.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.dto.rest.ConfirmRegistrationDto;
import com.example.dto.rest.SignInDto;
import com.example.dto.rest.SignUpDto;
import com.example.dto.rest.TokenDto;
import com.example.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@Tag(name = "Авторизация и регистрация пользователя", description = "Методы для авторизации, регистрации и подтверждения регистрации пользователя")
public class UserController {

    private final UserService service;

    @PostMapping("/signup")
    @Operation(summary = "Регистрация пользователя", description = "Получает DTO пользователя и создает нового пользователя")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Пользователь создан", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SignUpDto.class))))
    public SignUpDto signUp(@RequestBody SignUpDto dto) {
        return service.signUp(dto);
    }

    @PostMapping("/signin")
    @Operation(summary = "Авторизация пользователя", description = "Получает DTO пользователя и авторизует пользователя")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Пользователь авторизован", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenDto.class))))
    public TokenDto signIn(@RequestBody SignInDto dto) {
        return service.signIn(dto);
    }
    
    @PostMapping("/register/{generated-string}")
    @Operation(summary = "Подтверждение регистрации пользователя", description = "Получает DTO пользователя и подтверждает регистрацию пользователя")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Пользователь подтвержден", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ConfirmRegistrationDto.class))))
    public ConfirmRegistrationDto confirmRegistration(@PathVariable("generated-string") @Parameter(description = "Сгенерированный код подтверждения", example = "akjcsjhdsd21sdjggcsh") String generatedString, Authentication authentication) {
        return service.confirmRegistration(generatedString, authentication);
    }

}
