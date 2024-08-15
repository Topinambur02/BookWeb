package com.example.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AuthorDto {
    private Long id;
    private String name;
    private List<Long> bookIds = new ArrayList<>();
}
