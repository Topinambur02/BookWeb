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
public class AuthorRepositoryIT {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("DELETE FROM author");
        jdbcTemplate.execute("ALTER TABLE author ALTER COLUMN id RESTART WITH 1");
    }

    @Test
    public void testFindAll() {
        final var author1 = new Author();
        final var author2 = new Author();
        final var expected = List.of(author1, author2);

        repository.save(author1);
        repository.save(author2);

        final var actual = repository.findAll();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testSave() {
        final var expected = new Author();

        final var actual = repository.save(expected);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFindById() {
        final var id = 1L;
        final var expected = new Author();

        expected.setId(id);

        repository.save(expected);

        final var actual = repository
                .findById(id)
                .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

}
