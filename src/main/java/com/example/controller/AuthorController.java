package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AuthorDto;
import com.example.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("authors")
@Tag(name = "Методы для авторов", description = "Методы для получения списка авторов и создания автора")
public class AuthorController {

    private final AuthorService service;

    @GetMapping
    @Operation(summary = "Получить список всех авторов", description = "Возвращает список DTO всех авторов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Авторы получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class))),
    })
    public List<AuthorDto> getAll() {
        return service.getAll();
    }

    @PostMapping
    @Operation(summary = "Создать нового автора", description = "Получает DTO автора и создает нового автора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Автор успешно создан", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorDto.class))),
    })
    public AuthorDto create(@RequestBody AuthorDto dto) {
        return service.create(dto);
    }

}
