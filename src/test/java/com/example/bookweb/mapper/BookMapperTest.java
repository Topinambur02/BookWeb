package com.example.bookweb.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.dto.rest.BookDto;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.enums.Cover;
import com.example.mapper.BookMapper;

class BookMapperTest {

        private final BookMapper mapper = Mappers.getMapper(BookMapper.class);

        @Test
        void testBookToDto() {
                final var author = Author.builder().id(1L).build();
                final var authorId = author.getId();
                final var book = Book
                                .builder()
                                .id(1L)
                                .name("test")
                                .brand("test")
                                .count(1)
                                .cover(Cover.HARD)
                                .author(author)
                                .build();
                final var expected = BookDto
                                .builder()
                                .id(1L)
                                .name("test")
                                .brand("test")
                                .count(1)
                                .cover(Cover.HARD)
                                .authorId(authorId)
                                .build();
                final var actual = mapper.toDto(book);

                Assertions.assertThat(actual).isEqualTo(expected);
        }

        @Test
        void testDtoToBook() {
                final var author = Author.builder().id(1L).build();
                final var authorId = author.getId();
                final var dto = BookDto
                                .builder()
                                .id(1L)
                                .name("test")
                                .cover(Cover.HARD)
                                .count(1)
                                .brand("test")
                                .authorId(authorId)
                                .build();
                final var expected = Book
                                .builder()
                                .id(1L)
                                .name("test")
                                .brand("test")
                                .count(1)
                                .cover(Cover.HARD)
                                .author(author).build();
                final var actual = mapper.toBook(dto, author);

                Assertions.assertThat(actual).isEqualTo(expected);
        }

        @Test
        void testUpdate() {
                final var author = Author.builder().id(1L).build();
                final var authorId = author.getId();
                final var originalAuthor = Author.builder().id(3L).build();
                final var dto = BookDto
                                .builder()
                                .id(1L)
                                .name("test")
                                .cover(Cover.HARD)
                                .count(1)
                                .brand("test")
                                .authorId(authorId)
                                .build();
                final var expected = Book
                                .builder()
                                .id(1L)
                                .name("test1")
                                .brand("test1")
                                .count(2)
                                .cover(Cover.SOFT)
                                .author(originalAuthor)
                                .build();
                final var expectedId = expected.getId();
                final var expectedAuthorId = expected.getAuthor().getId();
                final var expectedCover = expected.getCover();
                final var expectedName = expected.getName();
                final var expectedBrand = expected.getBrand();
                final var expectedCount = expected.getCount();

                mapper.update(dto, author, expected);

                Assertions.assertThat(expectedId).isEqualTo(1L);
                Assertions.assertThat(expectedBrand).isEqualTo("test1");
                Assertions.assertThat(expectedName).isEqualTo("test1");
                Assertions.assertThat(expectedCount).isEqualTo(2);
                Assertions.assertThat(expectedCover).isEqualTo(Cover.SOFT);
                Assertions.assertThat(expectedAuthorId).isEqualTo(3L);
        }
        
}
