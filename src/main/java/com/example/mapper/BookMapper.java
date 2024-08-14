package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.dto.BookDto;
import com.example.entity.Book;

@Mapper
public interface BookMapper {
    Book toBook(BookDto dto);
    BookDto toDto(Book book);
    void update(BookDto dto, @MappingTarget Book book);
}
