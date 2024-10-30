package com.example.bookweb.unitTests.dto;

import com.example.bookweb.SerializationAndDeserializationDto;
import org.assertj.core.api.Assertions;

import java.util.List;

import com.example.dto.rest.AuthorDto;

class AuthorDtoTest extends SerializationAndDeserializationDto<AuthorDto> {

    @Override
    protected AuthorDto createDto() {
        return AuthorDto
                .builder()
                .id(1L)
                .name("test")
                .bookIds(List.of())
                .build();
    }

    @Override
    protected void validateDtoFields(AuthorDto dto) {
        Assertions.assertThat(dto)
                .usingRecursiveAssertion()
                .isEqualTo(AuthorDto
                        .builder()
                        .id(1L)
                        .name("test")
                        .bookIds(List.of())
                        .build());
    }
}
