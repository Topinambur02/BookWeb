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
public class ConfirmRegistrationDto {

    @Schema(description = "Подтверждение регистрации", example = "true")
    private Boolean confirmation;
    
}
