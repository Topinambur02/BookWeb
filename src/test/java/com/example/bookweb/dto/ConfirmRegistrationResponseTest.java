package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.ConfirmRegistrationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfirmRegistrationResponseTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/ConfirmRegistrationResponseTestSerialization.json").toFile();

        final var response = ConfirmRegistrationResponse
                .builder()
                .confirmation(true)
                .build();

        final var expected = mapper.readTree(json).toString();

        final var actual = mapper.writeValueAsString(response);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/ConfirmRegistrationResponseTestDeserialization.json").toFile();
        final var response = mapper.readValue(json, ConfirmRegistrationResponse.class);

        final var expected = response.getConfirmation();

        Assertions.assertThat(true).isEqualTo(expected);
    }

}
