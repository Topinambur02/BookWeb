package com.example.bookweb.unitTests.dto;

import com.example.bookweb.SerializationAndDeserializationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import com.example.dto.rest.SignUpDto;
import com.fasterxml.jackson.databind.ObjectMapper;

class SignUpDtoTest extends SerializationAndDeserializationDto<SignUpDto> {

    @Override
    protected SignUpDto createDto() {
        return SignUpDto
                .builder()
                .id(1L)
                .username("test")
                .password("test")
                .email("test")
                .build();
    }

    @Override
    protected void validateDtoFields(SignUpDto dto) {
        Assertions
                .assertThat(dto)
                .usingRecursiveAssertion()
                .isEqualTo(SignUpDto
                        .builder()
                        .id(1L)
                        .username("test")
                        .password("test")
                        .email("test")
                        .build());
    }
}
