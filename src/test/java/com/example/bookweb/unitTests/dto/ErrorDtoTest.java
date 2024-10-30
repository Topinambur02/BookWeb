package com.example.bookweb.unitTests.dto;

import com.example.bookweb.SerializationAndDeserializationDto;
import org.assertj.core.api.Assertions;

import com.example.dto.rest.ErrorDto;

class ErrorDtoTest extends SerializationAndDeserializationDto<ErrorDto> {

    @Override
    protected ErrorDto createDto() {
        return ErrorDto
                .builder()
                .message("test")
                .date("30-08-2024 12:22:44.213")
                .url("/api/boo")
                .username("login")
                .build();
    }

    @Override
    protected void validateDtoFields(ErrorDto dto) {
        Assertions
                .assertThat(dto)
                .usingRecursiveAssertion()
                .isEqualTo(ErrorDto
                        .builder()
                        .message("test")
                        .date("30-08-2024 12:22:44.213")
                        .url("/api/boo")
                        .username("login")
                        .build());
    }
}
