package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @Schema(description = "Сообщение об ошибке", example = "No endpoint GET /api/boo.")
    private String message;

    @Schema(description = "Время возникновения ошибки", example = "30-08-2024 12:22:44.213")
    private String date;

    @Schema(description = "URL запроса", example = "/api/boo")
    private String url;

    @Schema(description = "Имя пользователя", example = "login")
    private String user;
}
