package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.rest.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;

class ErrorDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/ErrorResponseTestSerialization.json").toFile();
        final var response = ErrorDto
                .builder()
                .message("test")
                .date("30-08-2024 12:22:44.213")
                .url("/api/boo")
                .username("login")
                .build();   
        final var expected = mapper.readTree(json).toString();
        final var actual = mapper.writeValueAsString(response);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/ErrorResponseTestDeserialization.json").toFile();
        final var response = mapper.readValue(json, ErrorDto.class);
        final var message = response.getMessage();
        final var date = response.getDate();
        final var url = response.getUrl();
        final var user = response.getUsername();

        Assertions.assertThat(message).isEqualTo("test");
        Assertions.assertThat(date).isEqualTo("30-08-2024 12:22:44.213");
        Assertions.assertThat(url).isEqualTo("/api/boo");
        Assertions.assertThat(user).isEqualTo("login");
    }
    
}
