package com.example.mapper;

import org.mapstruct.Mapper;

import com.example.dto.BookDto;
import com.example.entities.Book;

@Mapper
public interface BookMapper {
    Book toBook(BookDto bookDto);
    BookDto toDto(Book book);
}
