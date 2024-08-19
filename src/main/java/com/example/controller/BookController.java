package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BookDto;
import com.example.filter.BookFilter;
import com.example.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {
    private final BookService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BookDto create(@RequestBody BookDto dto) {
        return service.create(dto);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/filter")
    public List<BookDto> filter(@RequestBody BookFilter filter) {
        return service.filter(filter);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BookDto update(@RequestBody BookDto dto) {
        return service.update(dto.getId(), dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
    }
}
