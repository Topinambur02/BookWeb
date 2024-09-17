package com.example.dto;

import com.example.enums.Cover;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BookDto {

    @Schema(description = "ID книги", example = "1")
    private Long id;

    @Schema(description = "Название книги", example = "Война и мир")
    private String name;

    @Schema(description = "Обложка книги", example = "HARD")
    private Cover cover;

    @Schema(description = "Жанр книги", example = "Классика")
    private String brand;

    @Schema(description = "Количество книг", example = "1")
    private Integer count;

    @Schema(description = "ID автора", example = "1")
    private Long authorId;
}
