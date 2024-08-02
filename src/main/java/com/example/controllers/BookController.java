package com.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Book;
import com.example.services.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity.ok(this.bookService.createBook(book));
    }
    
    @GetMapping
    public List<Book> getAll() {
        return this.bookService.getAllBooks();
    }

    @PutMapping
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {
        return this.bookService.updateBook(id, book);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@PathVariable( value = "id") Long id) {
        return this.bookService.deleteBook(id);
    }
}
