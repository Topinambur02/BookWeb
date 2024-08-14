package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.BookDto;
import com.example.mapper.BookMapper;
import com.example.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    private final BookMapper mapper;

    public List<BookDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public BookDto create(BookDto dto) {
        final var book = mapper.toBook(dto);
        repository.save(book);
        return mapper.toDto(book);
    }

    public BookDto update(Long id, BookDto dto) {
        final var updatedBook = repository.findById(id).get();
        mapper.update(dto, updatedBook);
        return mapper.toDto(updatedBook);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
