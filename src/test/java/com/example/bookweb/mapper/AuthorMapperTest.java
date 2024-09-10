package com.example.bookweb.mapper;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.dto.AuthorDto;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.mapper.AuthorMapper;

public class AuthorMapperTest {

    private final AuthorMapper mapper = Mappers.getMapper(AuthorMapper.class);
    
    @Test
    public void testAuthorToDto() {
        final var author = new Author();
        author.setId(1L);
        author.setFirstName("test");
        author.setLastName("test");
        author.setBooks(List.of());

        final var expected = new AuthorDto();
        expected.setId(1L);
        expected.setName("test test");
        expected.setBookIds(List.of());

        final var actual = mapper.toDto(author);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDtoToAuthor() {
        final var book1 = new Book();
        book1.setId(1L);
        final var book2 = new Book();
        book2.setId(2L);
        final var books = List.of(book1, book2);

        final var dto = new AuthorDto();
        dto.setId(1L);
        dto.setName("test test");
        dto.setBookIds(List.of(1L, 2L));

        final var expected = new Author();
        expected.setId(1L);
        expected.setFirstName("test");
        expected.setLastName("test");
        expected.setBooks(books);

        final var actual = mapper.toAuthor(dto, books);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
    
}
