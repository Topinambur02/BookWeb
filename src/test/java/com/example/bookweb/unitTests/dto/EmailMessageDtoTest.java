package com.example.bookweb.unitTests.dto;

import com.example.bookweb.SerializationAndDeserializationDto;
import com.example.dto.rest.EmailMessageDto;
import org.assertj.core.api.Assertions;

class EmailMessageDtoTest extends SerializationAndDeserializationDto<EmailMessageDto> {

    @Override
    protected EmailMessageDto createDto() {
        return EmailMessageDto
                .builder()
                .to("test")
                .subject("test")
                .text("test")
                .build();
    }

    @Override
    protected void validateDtoFields(EmailMessageDto dto) {
        Assertions
                .assertThat(dto)
                .usingRecursiveAssertion()
                .isEqualTo(EmailMessageDto
                        .builder()
                        .to("test")
                        .subject("test")
                        .text("test")
                        .build());
    }
}
