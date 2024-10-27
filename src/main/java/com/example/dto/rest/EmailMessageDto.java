package com.example.dto.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessageDto {
    
    @Schema(description = "Получатель письма", example = "Получатель письма")
    private String to;

    @Schema(description = "Тема письма", example = "Тема письма")
    private String subject;
    
    @Schema(description = "Текст письма", example = "Текст письма")
    private String text;

}
