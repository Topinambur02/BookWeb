package com.example.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.dto.AuthorDto;
import com.example.entity.Author;
import com.example.entity.Book;

@Mapper
public interface AuthorMapper {

    @Mapping(target = "name", source = ".", qualifiedByName = "fullName")
    @Mapping(target = "bookIds", source = "books", qualifiedByName = "toBookIds")
    AuthorDto toDto(Author author);

    @Named("fullName")
    default String fullName(Author author) {
        return author.getFirstName() + " " + author.getLastName();
    }

    @Named("toBookIds")
    default List<Long> toBookIds(List<Book> books) {
        return books
                .stream()
                .map(Book::getId)
                .toList();
    }

    @Mapping(target = "books", source = "books")
    @Mapping(target = "firstName", source = "dto.name", qualifiedByName = "firstName")
    @Mapping(target = "lastName", source = "dto.name", qualifiedByName = "lastName")
    Author toAuthor(AuthorDto dto, List<Book> books);

    @Named("firstName")
    default String firstName(String authorName) {
        return authorName.split(" ")[0];
    }

    @Named("lastName")
    default String lastName(String authorName) {
        return authorName.split(" ")[1];
    }

}
