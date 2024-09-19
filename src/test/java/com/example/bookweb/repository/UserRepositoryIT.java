package com.example.bookweb.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.entity.User;
import com.example.repository.UserRepository;

@DataJpaTest
public class UserRepositoryIT {

    @Autowired
    private UserRepository repository;

    @Test
    public void testSave() {
        final var expected = new User();

        final var actual = repository.save(expected);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testExistsByUsername() {
        final var username = "test";

        final var user = new User();
        user.setUsername(username);

        final var expected = true;

        repository.save(user);

        final var actual = repository
                .existsByUsername(username);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFindByUsername() {
        final var username = "test";
        final var expected = new User();

        expected.setUsername(username);

        repository.save(expected);

        final var actual = repository
                .findByUsername(username)
                .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
