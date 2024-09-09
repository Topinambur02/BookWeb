package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.dto.SignInDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SignInDtoTest {
    
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialization() throws Exception {
        final var dto = new SignInDto();
        dto.setId(1L);
        dto.setUsername("test");
        dto.setPassword("test");

        final var expected = "{\"id\":1,\"username\":\"test\",\"password\":\"test\"}";

        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeserialization() throws Exception {
        final var json = "{\"id\":1,\"username\":\"test\",\"password\":\"test\"}";
        final var dto = mapper.readValue(json, SignInDto.class);

        final var id = dto.getId();
        final var username = dto.getUsername();
        final var password = dto.getPassword();

        Assertions.assertThat(1L).isEqualTo(id);
        Assertions.assertThat("test").isEqualTo(username);
        Assertions.assertThat("test").isEqualTo(password);
    }

}
