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

import com.example.entity.Book;
import com.example.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("books")
public class BookController {
    private final BookService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Book create(@RequestBody Book book) {
        return service.create(book);
    }
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll() {
        return service.getAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Book update(@RequestBody Book book) {
        return service.update(book.getId(), book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
    }
}
