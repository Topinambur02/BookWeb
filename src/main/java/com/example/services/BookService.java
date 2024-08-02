package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entities.Book;
import com.example.repositories.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return this.bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book updatedBook = this.bookRepository.findById(id).get();

        updatedBook.setName(book.getName());
        updatedBook.setBrand(book.getBrand());
        updatedBook.setCover(book.getCover());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setCount(book.getCount());

        return updatedBook;
    }

    public void deleteBook(Long id) {
        this.bookRepository.deleteById(id);
    }

}
