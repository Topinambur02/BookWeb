package com.example.bookweb.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.example.dto.EmailMessageDto;
import com.example.mapper.EmailMessageMapper;
import com.example.wrapper.SimpleMailMessageWrapper;

public class EmailMessageMapperTest {
    
    private final EmailMessageMapper mapper = Mappers.getMapper(EmailMessageMapper.class);

    @Test
    public void testToSimpleMailMessage() {
        final var dto = EmailMessageDto.builder()
                .to("test")
                .text("test")
                .build();

        final var expected = SimpleMailMessageWrapper
                .builder()
                .to("test")
                .text("test")
                .build();

        final var actual = mapper.toSimpleMailMessage(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
        
    }

}
