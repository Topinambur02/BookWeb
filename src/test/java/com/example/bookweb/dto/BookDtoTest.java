package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.rest.BookDto;
import com.example.enums.Cover;
import com.fasterxml.jackson.databind.ObjectMapper;

class BookDtoTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/BookDtoTestSerialization.json").toFile();
        final var dto = BookDto
                .builder()
                .id(1L)
                .name("test")
                .brand("test")
                .cover(Cover.SOFT)
                .authorId(1L)
                .count(1)
                .build();
        final var expected = mapper.readTree(json).toString();
        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/BookDtoTestDeserialization.json").toFile();
        final var dto = mapper.readValue(json, BookDto.class);
        final var id = dto.getId();
        final var name = dto.getName();
        final var brand = dto.getBrand();
        final var cover = dto.getCover();
        final var authorId = dto.getAuthorId();
        final var count = dto.getCount();

        Assertions.assertThat(id).isEqualTo(1L);
        Assertions.assertThat(name).isEqualTo("test");
        Assertions.assertThat(brand).isEqualTo("test");
        Assertions.assertThat(cover).isEqualTo(Cover.SOFT);
        Assertions.assertThat(authorId).isEqualTo(1L);
        Assertions.assertThat(count).isEqualTo(1);
    }

}
