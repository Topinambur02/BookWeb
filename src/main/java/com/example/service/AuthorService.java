package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.rest.AuthorDto;
import com.example.mapper.AuthorMapper;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorMapper mapper;
    private final AuthorRepository repository;
    private final BookRepository bookRepository;

    public List<AuthorDto> getAll() {
        return repository
                .findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public AuthorDto create(AuthorDto dto) {
        final var ids = dto.getBookIds();
        final var books = bookRepository.findAllById(ids);
        final var author = mapper.toAuthor(dto, books);
        final var savedAuthor = repository.save(author);

        return mapper.toDto(savedAuthor);
    }
    
}
