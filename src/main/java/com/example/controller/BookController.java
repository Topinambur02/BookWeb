package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BookDto;
import com.example.filter.BookFilter;
import com.example.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
@Tag(name = "Методы для книг", description = "Методы для создания книги, получения списка книг, фильтрации книг, изменения книги и удаления книги")
public class BookController {

    private final BookService service;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга успешно создана", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
    })
    @Operation(summary = "Создать новую книгу", description = "Получает DTO книги и создает новую книгу")
    public BookDto create(@RequestBody BookDto dto) {
        return service.create(dto);
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книги получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
    })
    @Operation(summary = "Получить список всех книг", description = "Возвращает список DTO всех книг")
    public List<BookDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книги получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
    })
    @Operation(summary = "Получить список книг по фильтру", description = "Возвращает список DTO книг по фильтру")
    public List<BookDto> filter(@RequestBody BookFilter filter) {
        return service.filter(filter);
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга обновлена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
    })
    @Operation(summary = "Изменить книгу", description = "Получает DTO книги и обновляет книгу")
    public BookDto update(@RequestBody BookDto dto) {
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга удалена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class))),
    })
    @Operation(summary = "Удалить книгу", description = "Удаляет книгу по ID")
    public Long delete(@PathVariable @Parameter(description = "ID книги", example = "1") Long id) {
        return service.delete(id);
    }
}
