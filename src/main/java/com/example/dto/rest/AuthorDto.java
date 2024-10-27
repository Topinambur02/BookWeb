package com.example.dto.rest;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AuthorDto {
    
    @Schema(description = "ID автора", example = "1")
    private Long id;

    @Schema(description = "Имя автора", example = "Александр Пушкин") 
    private String name;

    @Schema(description = "ID книги", example = "1, 2")
    private List<Long> bookIds;
    
}
