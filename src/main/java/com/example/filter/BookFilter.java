package com.example.filter;

import com.example.enums.Cover;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookFilter {

    @Schema(description = "Название книги", example = "Война и мир")
    private String name;

    @Schema(description = "Обложка книги", example = "HARD")
    private Cover cover;

    @Schema(description = "Жанр книги", example = "Классика")
    private String brand;

    @Schema(description = "Количество книг", example = "1")
    private Integer count;

    @Schema(description = "Фамилия автора", example = "Пушкин")
    private String authorLastName;

    @Schema(description = "Имя автора", example = "Александр")
    private String authorFirstName;
    
}
