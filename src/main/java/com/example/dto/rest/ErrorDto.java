package com.example.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    @Schema(description = "URL запроса", example = "/api/boo")
    private String url;

    @Schema(description = "Время возникновения ошибки", example = "30-08-2024 12:22:44.213")
    private String date;

    @Schema(description = "Имя пользователя", example = "login")
    private String username;

    @Schema(description = "Сообщение об ошибке", example = "No endpoint GET /api/boo.")
    private String message;
    
}
