package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BookDto;
import com.example.entities.Book;
import com.example.mapper.BookMapper;
import com.example.repositories.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public List<BookDto> getAllBooks() {
        return this.bookRepository.findAll()
                                  .stream()
                                  .map(this.bookMapper::toDto)
                                  .toList();
    }

    public BookDto createBook(BookDto bookDto) {
        Book book = this.bookMapper.toBook(bookDto);
        book = this.bookRepository.save(book);
        return this.bookMapper.toDto(book);
    }

    public BookDto updateBook(Long id, BookDto bookDto) {
        Book updatedBook = this.bookRepository.findById(id).get();

        updatedBook.setName(bookDto.getName());
        updatedBook.setBrand(bookDto.getBrand());
        updatedBook.setCover(bookDto.getCover());
        updatedBook.setAuthor(bookDto.getAuthor());
        updatedBook.setCount(bookDto.getCount());

        return this.bookMapper.toDto(updatedBook);
    }

    public void deleteBook(Long id) {
        this.bookRepository.deleteById(id);
    }

}
