package com.example.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}
