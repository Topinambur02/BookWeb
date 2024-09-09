package com.example.bookweb.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.dto.SignInDto;
import com.example.dto.SignUpDto;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.mapper.UserMapper;

public class UserMapperTest {
    
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testDtoToUser() {

        final var dto = new SignUpDto();
        dto.setId(1L);
        dto.setEmail("test@mail.ru");
        dto.setUsername("test");
        dto.setPassword("test");

        final var expected = new User();
        expected.setId(1L);
        expected.setEmail("test@mail.ru");
        expected.setUsername("test");
        expected.setPassword("test");
        expected.setRole(Role.GUEST);

        final var actual = mapper.toUser(dto);

        final var actualPassword = actual.getPassword();
        final var expectedPassword = expected.getPassword();
        final var isMatches = new BCryptPasswordEncoder().matches(expectedPassword, actualPassword);

        Assertions.assertThat(actual).isEqualTo(expected);
        Assertions.assertThat(isMatches).isTrue();
    }

    @Test
    public void testUserToSignInDto() {
        final var user = new User();
        user.setUsername("test");
        user.setPassword("test");

        final var expected = new SignInDto();
        expected.setUsername("test");
        expected.setPassword("test");

        final var actual = mapper.toSignInDto(user);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testUserToSignUpDto() {
        final var user = new User();
        user.setUsername("test");
        user.setEmail("test@mail.ru");
        user.setPassword("test");

        final var expected = new SignUpDto();
        expected.setUsername("test");
        expected.setEmail("test@mail.ru");
        expected.setPassword("test");

        final var actual = mapper.toSignUpDto(user);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}

