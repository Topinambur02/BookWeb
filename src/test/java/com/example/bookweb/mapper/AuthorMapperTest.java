package com.example.bookweb.mapper;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.dto.rest.AuthorDto;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.mapper.AuthorMapper;

class AuthorMapperTest {

        private final AuthorMapper mapper = Mappers.getMapper(AuthorMapper.class);

        @Test
        void testAuthorToDto() {
                final var author = Author
                                .builder()
                                .id(1L)
                                .firstName("test")
                                .lastName("test")
                                .books(List.of())
                                .build();
                final var expected = AuthorDto
                                .builder()
                                .id(1L)
                                .name("test test")
                                .bookIds(List.of())
                                .build();
                final var actual = mapper.toDto(author);

                Assertions.assertThat(actual).isEqualTo(expected);
        }

        @Test
        void testDtoToAuthor() {
                final var book1 = Book.builder().id(1L).build();
                final var book2 = Book.builder().id(2L).build();
                final var books = List.of(book1, book2);
                final var dto = AuthorDto
                                .builder()
                                .id(1L)
                                .name("test test")
                                .bookIds(List.of(1L, 2L))
                                .build();
                final var expected = Author
                                .builder()
                                .id(1L)
                                .firstName("test")
                                .lastName("test")
                                .books(books)
                                .build();
                final var actual = mapper.toAuthor(dto, books);

                Assertions.assertThat(actual).isEqualTo(expected);
        }

}
