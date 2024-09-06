package com.example.bookweb.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.entity.Book;
import com.example.repository.BookRepository;

@DataJpaTest
public class BookRepositoryIT {

    @Autowired
    private BookRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE book RESTART IDENTITY");
    }

    @Test
    public void testFindAll() {
        final var book1 = new Book();
        final var book2 = new Book();
        final var expected = List.of(book1, book2);

        repository.save(book1);
        repository.save(book2);

        final var actual = repository.findAll();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFindAllById() {
        final var book1 = new Book();

        book1.setId(1L);

        final var book2 = new Book();

        book2.setId(2L);

        final var book3 = new Book();

        book3.setId(3L);

        final var ids = List.of(1L, 2L);
        final var expected = List.of(book1, book2);
        
        repository.save(book1);
        repository.save(book2);
        repository.save(book3);

        final var actual = repository.findAllById(ids);

        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void testSave() {
        final var expected = new Book();

        final var actual = repository.save(expected);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFindById() {
        final var id = 1L;
        final var expected = new Book();

        expected.setId(id);

        repository.save(expected);

        final var actual = repository
                .findById(id)
                .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testDeleteById() {
        final var id = 1L;
        final var book = new Book();

        book.setId(id);

        repository.save(book);
        repository.deleteById(id);
        final var actual = repository.findById(id).isEmpty();

        Assertions.assertThat(actual).isTrue();
    }

}
