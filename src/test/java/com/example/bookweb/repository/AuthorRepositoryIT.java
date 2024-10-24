package com.example.bookweb.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.entity.Author;
import com.example.repository.AuthorRepository;

@DataJpaTest
class AuthorRepositoryIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AuthorRepository repository;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM author");
        jdbcTemplate.execute("ALTER TABLE author ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    void testFindAll() {
        final var author1 = Author.builder().build();
        final var author2 = Author.builder().build();
        final var expected = List.of(author1, author2);

        repository.save(author1);
        repository.save(author2);

        final var actual = repository.findAll();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testSave() {
        final var expected = Author.builder().build();
        final var actual = repository.save(expected);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testFindById() {
        final var id = 1L;
        final var expected = Author
                .builder()
                .id(id)
                .build();

        repository.save(expected);

        final var actual = repository
                .findById(id)
                .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
