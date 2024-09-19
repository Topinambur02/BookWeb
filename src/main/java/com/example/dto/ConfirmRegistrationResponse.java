package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ConfirmRegistrationResponse {
    @Schema(description = "Подтверждение регистрации", example = "true")
    private Boolean confirmation;
}
