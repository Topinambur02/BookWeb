package com.example.bookweb.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.dto.BookDto;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.enums.Cover;
import com.example.mapper.BookMapper;

public class BookMapperTest {
    
    private final BookMapper mapper = Mappers.getMapper(BookMapper.class);

    @Test
    public void testBookToDto() {
        final var author = new Author();
        author.setId(1L);
        final var authorId = author.getId();

        final var book = new Book();
        book.setId(1L);
        book.setName("test");
        book.setBrand("test");
        book.setCount(1);
        book.setCover(Cover.HARD);
        book.setAuthor(author);

        final var expected = new BookDto();
        expected.setId(1L);
        expected.setName("test");
        expected.setBrand("test");
        expected.setCount(1);
        expected.setCover(Cover.HARD);
        expected.setAuthorId(authorId);

        final var actual = mapper.toDto(book);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDtoToBook() {
        final var author = new Author();
        author.setId(1L);
        final var authorId = author.getId();

        final var dto = new BookDto();
        dto.setId(1L);
        dto.setName("test");
        dto.setCover(Cover.HARD);
        dto.setCount(1);
        dto.setBrand("test");
        dto.setAuthorId(authorId);

        final var expected = new Book();
        expected.setId(1L);
        expected.setName("test");
        expected.setBrand("test");
        expected.setCount(1);
        expected.setCover(Cover.HARD);
        expected.setAuthor(author);

        final var actual = mapper.toBook(dto, author);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testUpdate() {
        final var author = new Author();
        author.setId(1L);
        final var authorId = author.getId();

        final var dto = new BookDto();
        dto.setId(1L);
        dto.setName("test");
        dto.setCover(Cover.HARD);
        dto.setCount(1);
        dto.setBrand("test");
        dto.setAuthorId(authorId);

        final var expected = new Book();
        expected.setId(1L);
        expected.setName("test1");
        expected.setBrand("test1");
        expected.setCount(2);
        expected.setCover(Cover.SOFT);

        final var originalAuthor = new Author();
        originalAuthor.setId(3L);

        expected.setAuthor(originalAuthor);

        final var expectedId = expected.getId();
        final var expectedAuthorId = expected.getAuthor().getId();
        final var expectedCover = expected.getCover();
        final var expectedName = expected.getName();
        final var expectedBrand = expected.getBrand();
        final var expectedCount = expected.getCount();

        mapper.update(dto, author, expected);

        Assertions.assertThat(1L).isEqualTo(expectedId);
        Assertions.assertThat("test1").isEqualTo(expectedBrand);
        Assertions.assertThat("test1").isEqualTo(expectedName);
        Assertions.assertThat(2).isEqualTo(expectedCount);
        Assertions.assertThat(Cover.SOFT).isEqualTo(expectedCover);
        Assertions.assertThat(3L).isEqualTo(expectedAuthorId);
    }
}
