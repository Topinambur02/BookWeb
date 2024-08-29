package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenResponse {
    @Schema(description = "Токен", example = "eyJhbGcQT4fwpMeJf36POk6yJV_adQssw5c")
    private String token;
}
