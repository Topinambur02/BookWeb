package com.example.bookweb.unitTests.dto;

import com.example.bookweb.SerializationAndDeserializationDto;
import org.assertj.core.api.Assertions;

import com.example.dto.rest.TokenDto;

class TokenDtoTest extends SerializationAndDeserializationDto<TokenDto> {

    @Override
    protected TokenDto createDto() {
        return TokenDto
                .builder()
                .token("test")
                .build();
    }

    @Override
    protected void validateDtoFields(TokenDto dto) {
        final var token = dto.getToken();

        Assertions.assertThat(token).isEqualTo("test");
    }
}
