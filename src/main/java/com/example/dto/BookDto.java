package com.example.dto;

import com.example.enums.Cover;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BookDto {

    @Schema(description = "ID книги", example = "1")
    private Long id;

    @Schema(description = "Название книги", example = "Война и мир")
    private String name;

    @Schema(description = "Жанр книги", example = "Классика")
    private String brand;

    @Schema(description = "Обложка книги", example = "HARD")
    private Cover cover;

    @Schema(description = "ID автора", example = "1")
    private Long authorId;

    @Schema(description = "Количество книг", example = "1")
    private Integer count;
}
