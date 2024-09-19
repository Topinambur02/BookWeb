package com.example.bookweb.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.example.dto.BookDto;
import com.example.enums.Cover;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookDtoTest {
    
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testSerialization() throws Exception {
        final var dto = new BookDto();
        dto.setId(1L);
        dto.setName("test");
        dto.setBrand("test");
        dto.setCover(Cover.SOFT);
        dto.setAuthorId(1L);
        dto.setCount(1);

        final var expected = "{\"id\":1,\"name\":\"test\",\"brand\":\"test\",\"cover\":\"SOFT\",\"authorId\":1,\"count\":1}";

        final var actual = mapper.writeValueAsString(dto);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeserialization() throws Exception {
        final var json = "{\"id\":1,\"name\":\"test\",\"brand\":\"test\",\"cover\":\"SOFT\",\"authorId\":1,\"count\":1}";
        final var dto = mapper.readValue(json, BookDto.class);

        final var id = dto.getId();
        final var name = dto.getName();
        final var brand = dto.getBrand();
        final var cover = dto.getCover();
        final var authorId = dto.getAuthorId();
        final var count = dto.getCount();

        Assertions.assertThat(1L).isEqualTo(id);
        Assertions.assertThat("test").isEqualTo(name);
        Assertions.assertThat("test").isEqualTo(brand);
        Assertions.assertThat(Cover.SOFT).isEqualTo(cover);
        Assertions.assertThat(1L).isEqualTo(authorId);
        Assertions.assertThat(1).isEqualTo(count);
    }

}
