package com.example.bookweb.unitTests.dto;

import com.example.bookweb.SerializationAndDeserializationDto;
import org.assertj.core.api.Assertions;

import com.example.dto.rest.SignInDto;

class SignInDtoTest extends SerializationAndDeserializationDto<SignInDto> {

    @Override
    protected SignInDto createDto() {
        return SignInDto
                .builder()
                .id(1L)
                .username("test")
                .password("test")
                .build();
    }

    @Override
    protected void validateDtoFields(SignInDto dto) {
        Assertions
                .assertThat(dto)
                .usingRecursiveAssertion()
                .isEqualTo(SignInDto
                        .builder()
                        .id(1L)
                        .username("test")
                        .password("test")
                        .build());
    }
}
