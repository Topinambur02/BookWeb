package com.example.bookweb.dto;

import java.nio.file.Paths;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.dto.kafka.KafkaEmailMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;

class EmailMessageDtoTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testDeserialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/EmailMessageTestDeserialization.json").toFile();
        final var dto = mapper.readValue(json, KafkaEmailMessageDto.class);
        final var text = dto.getText();
        final var to = dto.getTo();

        Assertions.assertThat(text).isEqualTo("test");
        Assertions.assertThat(to).isEqualTo("test");
    }

    @Test
    void testSerialization() throws Exception {
        final var json = Paths.get("src/test/resources/json/EmailMessageTestSerialization.json").toFile();
        final var dto = KafkaEmailMessageDto
                .builder()
                .to("test")
                .text("test")
                .build();
        final var expected = mapper.readTree(json).toString();
        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
