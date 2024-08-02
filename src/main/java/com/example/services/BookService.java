package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entities.Book;
import com.example.repositories.BookRepository;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        try {
            return this.bookRepository.findAll();
        } 
        
        catch(NullPointerException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }

    public ResponseEntity<Book> updateBook(Long id, Book book) {
        Book updatedBook = this.bookRepository.findById(id).get();

        updatedBook.setName(book.getName());
        updatedBook.setBrand(book.getBrand());
        updatedBook.setCover(book.getCover());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setCount(book.getCount());

        return ResponseEntity.ok(updatedBook);
    }

    public ResponseEntity<String> deleteBook(Long id) {
        this.bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
