package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BookDto;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.BookMapper;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final AuthorRepository authorRepository;
    private final BookMapper mapper;

    public List<BookDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public BookDto create(BookDto dto) {
        final var author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        final var book = mapper.toBook(dto, author);
        final var savedBook = repository.save(book);

        return mapper.toDto(savedBook);
    }

    public BookDto update(Long id, BookDto dto) {
        final var updatedBook = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        final var author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        mapper.update(dto, author, updatedBook);
        return mapper.toDto(updatedBook);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
