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
class BookRepositoryIT {

    @Autowired
    private BookRepository repository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE book RESTART IDENTITY");
    }

    @Test
    void testFindAll() {
        final var book1 = Book.builder().build();
        final var book2 = Book.builder().build();
        final var expected = List.of(book1, book2);

        repository.save(book1);
        repository.save(book2);

        final var actual = repository.findAll();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testFindAllById() {
        final var book1 = Book.builder().id(1L).build();
        final var book2 = Book.builder().id(2L).build();
        final var book3 = Book.builder().id(3L).build();
        final var ids = List.of(1L, 2L);
        final var expected = List.of(book1, book2);

        repository.save(book1);
        repository.save(book2);
        repository.save(book3);

        final var actual = repository.findAllById(ids);

        Assertions.assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void testSave() {
        final var expected = Book.builder().build();

        final var actual = repository.save(expected);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testFindById() {
        final var id = 1L;
        final var expected = Book
                .builder()
                .id(id)
                .build();

        repository.save(expected);

        final var actual = repository
                .findById(id)
                .get();

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testDeleteById() {
        final var id = 1L;
        final var book = Book
                .builder()
                .id(id)
                .build();

        repository.save(book);
        repository.deleteById(id);
        
        final var actual = repository.findById(id).isEmpty();

        Assertions.assertThat(actual).isTrue();
    }

}
