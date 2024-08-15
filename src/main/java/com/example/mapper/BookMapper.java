package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.dto.BookDto;
import com.example.entity.Author;
import com.example.entity.Book;

@Mapper
public interface BookMapper {
    @Mapping(target = "author", source = "author")
    @Mapping(target = "id", source = "dto.id")
    Book toBook(BookDto dto, Author author);

    @Mapping(target = "authorId", source = "author.id")
    BookDto toDto(Book book);

    @Mapping(target = "author", source = "author")
    @Mapping(target = "id", source = "dto.id")
    void update(BookDto dto, Author author, @MappingTarget Book book);
}
