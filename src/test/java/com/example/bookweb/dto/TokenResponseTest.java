package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.dto.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenResponseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialization() throws Exception {
        final var dto = new TokenResponse();
        dto.setToken("test");

        final var expected = "{\"token\":\"test\"}";

        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeserialization() throws Exception {
        final var json = "{\"token\":\"test\"}";
        final var response = mapper.readValue(json, TokenResponse.class);

        final var token = response.getToken();

        Assertions.assertThat("test").isEqualTo(token);
    }
}
