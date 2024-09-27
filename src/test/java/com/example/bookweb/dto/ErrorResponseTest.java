package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ErrorResponseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/ErrorResponseTestSerialization.json").toFile();

        final var response = ErrorResponse
                .builder()
                .message("test")
                .date("30-08-2024 12:22:44.213")
                .url("/api/boo")
                .user("login")
                .build();
                
        final var expected = mapper.readTree(json).toString();

        final var actual = mapper.writeValueAsString(response);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/ErrorResponseTestDeserialization.json").toFile();
        final var response = mapper.readValue(json, ErrorResponse.class);

        final var message = response.getMessage();
        final var date = response.getDate();
        final var url = response.getUrl();
        final var user = response.getUser();

        Assertions.assertThat("test").isEqualTo(message);
        Assertions.assertThat("30-08-2024 12:22:44.213").isEqualTo(date);
        Assertions.assertThat("/api/boo").isEqualTo(url);
        Assertions.assertThat("login").isEqualTo(user);
    }
}
