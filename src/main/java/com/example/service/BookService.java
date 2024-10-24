package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.rest.BookDto;
import com.example.dto.rest.BookFilterDto;
import com.example.exception.ResourceNotFoundException;
import com.example.mapper.BookMapper;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import com.example.specification.BookSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper mapper;
    private final BookRepository repository;
    private final BookSpecification specification;
    private final AuthorRepository authorRepository;

    public List<BookDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public BookDto create(BookDto dto) {
        final var id = dto.getAuthorId();
        final var author = authorRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException
                        .builder()
                        .message("Author not found")
                        .build());
        final var book = mapper.toBook(dto, author);
        final var savedBook = repository.save(book);

        return mapper.toDto(savedBook);
    }

    public BookDto update(BookDto dto) {
        final var id = dto.getId();
        final var authorId = dto.getAuthorId();
        final var updatedBook = repository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException
                        .builder()
                        .message("Book not found")
                        .build());
        final var author = authorRepository.findById(authorId)
                .orElseThrow(() -> ResourceNotFoundException
                        .builder()
                        .message("Author not found")
                        .build());

        mapper.update(dto, author, updatedBook);

        final var savedBook = repository.save(updatedBook);

        return mapper.toDto(savedBook);
    }

    public Long delete(Long id) {
        final var book = repository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException
                        .builder()
                        .message("Book not found")
                        .build());
        final var findId = book.getId();

        repository.deleteById(findId);

        return id;
    }

    public List<BookDto> filter(BookFilterDto filter) {
        final var bookSpecification = specification.filter(filter);

        return repository
                .findAll(bookSpecification)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

}
