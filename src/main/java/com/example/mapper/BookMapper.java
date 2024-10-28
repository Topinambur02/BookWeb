package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.dto.rest.BookDto;
import com.example.entity.Author;
import com.example.entity.Book;

@Mapper
public interface BookMapper {

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "author", source = "author")
    Book toBook(BookDto dto, Author author);

    @Mapping(target = "authorId", source = "author.id")
    BookDto toDto(Book book);

    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "author", source = "author")
    void update(BookDto dto, Author author, @MappingTarget Book book);
    
}
