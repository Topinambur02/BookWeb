package com.example.bookweb.unitTests.dto;

import com.example.bookweb.SerializationAndDeserializationDto;
import org.assertj.core.api.Assertions;

import com.example.dto.rest.BookDto;
import com.example.enums.Cover;

class BookDtoTest extends SerializationAndDeserializationDto<BookDto> {

    @Override
    protected BookDto createDto() {
        return BookDto
                .builder()
                .id(1L)
                .name("test")
                .brand("test")
                .cover(Cover.SOFT)
                .authorId(1L)
                .count(1)
                .build();
    }

    @Override
    protected void validateDtoFields(BookDto dto) {
        Assertions.assertThat(dto)
                .usingRecursiveAssertion()
                .isEqualTo(BookDto
                        .builder()
                        .id(1L)
                        .name("test")
                        .brand("test")
                        .cover(Cover.SOFT)
                        .authorId(1L)
                        .count(1)
                        .build());
    }
}
