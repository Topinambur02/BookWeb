package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignInDto {

    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "login")
    private String username;

    @Schema(description = "Пароль пользователя", example = "password")
    private String password;
}
