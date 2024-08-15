package com.example.dto;

import com.example.enums.Cover;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String name;
    private String brand;
    private Cover cover;
    private Long authorId;
    private Integer count;
}
