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
public class TokenDto {

    @Schema(description = "Токен", example = "eyJhbGcQT4fwpMeJf36POk6yJV_adQssw5c")
    private String token;
    
}
