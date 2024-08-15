package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AuthorDto;
import com.example.service.AuthorService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("authors")
public class AuthorController {

    private final AuthorService service;

    @GetMapping
    public List<AuthorDto> getAll() {
        return service.getAll();
    }

    @PostMapping
    public AuthorDto create(@RequestBody AuthorDto dto) {
        return service.create(dto);
    }
    
}
