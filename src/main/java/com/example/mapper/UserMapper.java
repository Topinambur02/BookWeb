package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.dto.SignInDto;
import com.example.dto.SignUpDto;
import com.example.entity.User;

@Mapper
public interface UserMapper {

    @Mapping(target = "password", source = ".", qualifiedByName = "encodePassword")
    @Mapping(target = "role", constant = "GUEST")
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "confirmationCode", ignore = true)
    User toUser(SignUpDto user);

    @Named("encodePassword")
    default String encode(SignUpDto dto) {
        return new BCryptPasswordEncoder().encode(dto.getPassword());
    }

    SignInDto toSignInDto(User user);

    SignUpDto toSignUpDto(User user);
}
