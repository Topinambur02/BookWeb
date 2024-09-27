package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.SignUpDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SignUpDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/SignUpDtoTestSerialization.json").toFile();

        final var dto = SignUpDto
                .builder()
                .id(1L)
                .username("test")
                .password("test")
                .email("test")
                .build();

        final var expected = mapper.readTree(json).toString();

        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/SignUpDtoTestDeserialization.json").toFile();
        final var dto = mapper.readValue(json, SignUpDto.class);

        final var id = dto.getId();
        final var username = dto.getUsername();
        final var password = dto.getPassword();
        final var email = dto.getEmail();

        Assertions.assertThat(1L).isEqualTo(id);
        Assertions.assertThat("test").isEqualTo(username);
        Assertions.assertThat("test").isEqualTo(password);
        Assertions.assertThat("test").isEqualTo(email);
    }

}
