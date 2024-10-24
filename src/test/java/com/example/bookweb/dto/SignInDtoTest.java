package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.rest.SignInDto;
import com.fasterxml.jackson.databind.ObjectMapper;

class SignInDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/SignInDtoTestSerialization.json").toFile();
        final var dto = SignInDto
                .builder()
                .id(1L)
                .username("test")
                .password("test")
                .build();
        final var expected = mapper.readTree(json).toString();
        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/SingInDtoTestDeserialization.json").toFile();
        final var dto = mapper.readValue(json, SignInDto.class);
        final var id = dto.getId();
        final var username = dto.getUsername();
        final var password = dto.getPassword();

        Assertions.assertThat(id).isEqualTo(1L);
        Assertions.assertThat(username).isEqualTo("test");
        Assertions.assertThat(password).isEqualTo("test");
    }

}
