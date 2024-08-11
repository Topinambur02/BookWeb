package com.example.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BookDto;
import com.example.services.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok().body(this.bookService.createBook(bookDto));
    }
    
    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok().body(this.bookService.getAllBooks());
    }

    @PutMapping
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok().body(this.bookService.updateBook(bookDto.getId(), bookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        this.bookService.deleteBook(id);
        return ResponseEntity.ok().body("Book deleted");
    }
}
