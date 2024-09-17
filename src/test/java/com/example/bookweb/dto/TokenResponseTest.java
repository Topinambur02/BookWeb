package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenResponseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/TokenResponseTestSerialization.json").toFile();

        final var dto = TokenResponse
                .builder()
                .token("test")
                .build();

        final var expected = mapper.readTree(json).toString();

        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/TokenResponseTestDeserialization.json").toFile();
        final var response = mapper.readValue(json, TokenResponse.class);

        final var token = response.getToken();

        Assertions.assertThat("test").isEqualTo(token);
    }
}
