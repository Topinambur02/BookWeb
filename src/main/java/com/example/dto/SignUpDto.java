package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpDto {

    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя", example = "login")
    private String username;

    @Schema(description = "Почта пользователя", example = "test@gmail.com")
    private String email;

    @Schema(description = "Пароль пользователя", example = "password")
    private String password;
}
