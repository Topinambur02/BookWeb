package com.example.bookweb.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.entity.User;
import com.example.repository.UserRepository;

@DataJpaTest
class UserRepositoryIT {

    @Autowired
    private UserRepository repository;

    @Test
    void testSave() {
        final var expected = User.builder().build();

        final var actual = repository.save(expected);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testExistsByUsername() {
        final var username = "test";
        final var user = User
                .builder()
                .username(username)
                .build();

        repository.save(user);

        final var actual = repository.existsByUsername(username);

        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void testFindByUsername() {
        final var username = "test";
        final var expected = User
                .builder()
                .username("test")
                .build();

        repository.save(expected);

        final var actual = repository
                .findByUsername(username)
                .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
