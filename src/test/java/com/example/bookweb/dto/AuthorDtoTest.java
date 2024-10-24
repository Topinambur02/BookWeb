package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.nio.file.Paths;

import com.example.dto.rest.AuthorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

class AuthorDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/AuthorDtoTestSerialization.json").toFile();
        final var dto = AuthorDto
                .builder()
                .id(1L)
                .name("test")
                .bookIds(List.of())
                .build();
        final var expected = mapper.readTree(json).toString();
        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/AuthorDtoTestDeserialization.json").toFile();
        final var dto = mapper.readValue(json, AuthorDto.class);
        final var id = dto.getId();
        final var name = dto.getName();
        final var bookIds = dto.getBookIds();

        Assertions.assertThat(id).isEqualTo(1L);
        Assertions.assertThat(name).isEqualTo("test");
        Assertions.assertThat(List.of()).isEqualTo(bookIds);
    }
    
}
