package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.rest.ConfirmRegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;

class ConfirmRegistrationDtoTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/ConfirmRegistrationResponseTestSerialization.json").toFile();
        final var response = ConfirmRegistrationDto
                .builder()
                .confirmation(true)
                .build();
        final var expected = mapper.readTree(json).toString();
        final var actual = mapper.writeValueAsString(response);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/ConfirmRegistrationResponseTestDeserialization.json").toFile();
        final var response = mapper.readValue(json, ConfirmRegistrationDto.class);
        final var expected = response.getConfirmation();

        Assertions.assertThat(expected).isTrue();
    }

}
