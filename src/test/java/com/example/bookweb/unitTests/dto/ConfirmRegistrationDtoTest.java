package com.example.bookweb.unitTests.dto;

import com.example.bookweb.SerializationAndDeserializationDto;
import org.assertj.core.api.Assertions;

import com.example.dto.rest.ConfirmRegistrationDto;

class ConfirmRegistrationDtoTest extends SerializationAndDeserializationDto<ConfirmRegistrationDto> {

    @Override
    protected ConfirmRegistrationDto createDto() {
        return ConfirmRegistrationDto
                .builder()
                .confirmation(true)
                .build();
    }

    @Override
    protected void validateDtoFields(ConfirmRegistrationDto dto) {
        final var expected = dto.getConfirmation();

        Assertions.assertThat(expected).isTrue();
    }
}
