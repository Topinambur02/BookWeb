package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.dto.rest.SignInDto;
import com.example.dto.rest.SignUpDto;
import com.example.entity.User;

@Mapper
public interface UserMapper {

    @Mapping(target = "role", constant = "GUEST")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "confirmationCode", ignore = true)
    @Mapping(target = "password", source = ".", qualifiedByName = "encodePassword")
    User toUser(SignUpDto user);

    @Named("encodePassword")
    default String encode(SignUpDto dto) {
        return new BCryptPasswordEncoder().encode(dto.getPassword());
    }

    @Mapping(target = "role", constant = "GUEST")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "confirmationCode", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void update(SignUpDto dto, @MappingTarget User user);

    SignInDto toSignInDto(User user);

    SignUpDto toSignUpDto(User user);
    
}
