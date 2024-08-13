package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Book;
import com.example.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository repository;

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Book create(Book book) {
        return repository.save(book);
    }

    public Book update(Long id, Book book) {
        final var updatedBook = repository.findById(id).get();

        updatedBook.setName(book.getName());
        updatedBook.setBrand(book.getBrand());
        updatedBook.setCover(book.getCover());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setCount(book.getCount());

        return updatedBook;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
