package com.example.bookweb.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import com.example.dto.AuthorDto;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.mapper.AuthorMapper;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import com.example.service.AuthorService;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository repository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorMapper mapper;

    @InjectMocks
    private AuthorService service;
    
    @Test
    void testGetAll() {
        final var author1 = new Author();
        final var author2 = new Author();
        final var authorDto1 = new AuthorDto();
        final var authorDto2 = new AuthorDto();
        final var authors = List.of(author1, author2);
        final var expected = List.of(authorDto1, authorDto2);

        when(repository.findAll()).thenReturn(authors);
        when(mapper.toDto(author1)).thenReturn(authorDto1);
        when(mapper.toDto(author2)).thenReturn(authorDto2);

        final var actual = service.getAll();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testCreate() {
        final var id = 1L;
        final var expected = new AuthorDto();

        expected.setId(id);

        final var author = new Author();

        author.setId(id);

        final var mappedDto = new AuthorDto();

        mappedDto.setId(id);

        final var mappedAuthor = new Author();

        mappedAuthor.setId(id);

        final var books = List.of(new Book(), new Book());

        when(bookRepository.findAllById(expected.getBookIds())).thenReturn(books);
        when(repository.save(any(Author.class))).thenReturn(new Author());
        when(mapper.toAuthor(any(AuthorDto.class), any(List.class))).thenReturn(mappedAuthor);
        when(mapper.toDto(any(Author.class))).thenReturn(mappedDto);

        final var actual = service.create(expected);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
