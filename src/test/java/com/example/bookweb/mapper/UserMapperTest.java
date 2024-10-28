package com.example.bookweb.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.dto.rest.SignInDto;
import com.example.dto.rest.SignUpDto;
import com.example.entity.User;
import com.example.enums.Role;
import com.example.mapper.UserMapper;

class UserMapperTest {

        private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

        @Test
        void testDtoToUser() {
                final var dto = SignUpDto
                                .builder()
                                .id(1L)
                                .email("test@mail.ru")
                                .username("test")
                                .password("test")
                                .build();
                final var expected = User
                                .builder()
                                .id(1L)
                                .email("test@mail.ru")
                                .username("test")
                                .password("test")
                                .role(Role.GUEST)
                                .build();
                final var actual = mapper.toUser(dto);
                final var actualPassword = actual.getPassword();
                final var expectedPassword = expected.getPassword();
                final var isMatches = new BCryptPasswordEncoder().matches(expectedPassword, actualPassword);

                Assertions.assertThat(actual).isEqualTo(expected);
                Assertions.assertThat(isMatches).isTrue();
        }

        @Test
        void testUserToSignInDto() {
                final var user = User
                                .builder()
                                .username("test")
                                .password("test")
                                .build();
                final var expected = SignInDto
                                .builder()
                                .username("test")
                                .password("test")
                                .build();
                final var actual = mapper.toSignInDto(user);

                Assertions.assertThat(actual).isEqualTo(expected);
        }

        @Test
        void testUserToSignUpDto() {
                final var user = User
                                .builder()
                                .username("test")
                                .email("test@mail.ru")
                                .password("test")
                                .build();
                final var expected = SignUpDto
                                .builder()
                                .username("test")
                                .email("test@mail.ru")
                                .password("test")
                                .build();
                final var actual = mapper.toSignUpDto(user);

                Assertions.assertThat(actual).isEqualTo(expected);
        }
        
}
