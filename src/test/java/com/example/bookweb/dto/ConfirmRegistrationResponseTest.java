package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.dto.ConfirmRegistrationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConfirmRegistrationResponseTest {
    
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialization() throws Exception {
        final var response = new ConfirmRegistrationResponse();

        response.setConfirmation(true);

        final var expected = "{\"confirmation\":true}";

        final var actual = mapper.writeValueAsString(response);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeserialization() throws Exception {
        final var json = "{\"confirmation\":true}";
        final var response = mapper.readValue(json, ConfirmRegistrationResponse.class);

        final var expected = response.getConfirmation();

        Assertions.assertThat(true).isEqualTo(expected);
    }

}
