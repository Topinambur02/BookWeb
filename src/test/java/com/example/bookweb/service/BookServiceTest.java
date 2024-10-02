package com.example.bookweb.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.example.dto.BookDto;
import com.example.entity.Author;
import com.example.entity.Book;
import com.example.exception.ResourceNotFoundException;
import com.example.filter.BookFilter;
import com.example.mapper.BookMapper;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import com.example.service.BookService;
import com.example.specification.BookSpecification;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	@Mock
	private BookMapper mapper;
	@InjectMocks
	private BookService service;
	@Mock
	private BookRepository repository;
	@Mock
	private BookSpecification specification;
	@Mock
	private AuthorRepository authorRepository;

	@Test
	void testGetAll() {
		final var book1 = Book.builder().build();
		final var book2 = Book.builder().build();
		final var bookDto1 = BookDto.builder().build();
		final var bookDto2 = BookDto.builder().build();
		final var books = List.of(book1, book2);
		final var expected = List.of(bookDto1, bookDto2);

		when(repository.findAll()).thenReturn(books);
		when(mapper.toDto(book1)).thenReturn(bookDto1);
		when(mapper.toDto(book2)).thenReturn(bookDto2);

		final var actual = service.getAll();

		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testCreate() {
		final var id = 1L;
		final var authorId = 1L;

		final var author = Author
				.builder()
				.id(authorId)
				.build();
		final var mappedDto = BookDto
				.builder()
				.id(id)
				.authorId(authorId)
				.build();
		final var mappedBook = Book
				.builder()
				.id(id)
				.build();
		final var expected = BookDto
				.builder()
				.id(id)
				.authorId(authorId)
				.build();

		when(authorRepository.findById(id)).thenReturn(Optional.of(author));
		when(repository.save(any(Book.class))).thenReturn(Book.builder().build());
		when(mapper.toBook(any(BookDto.class), any(Author.class))).thenReturn(mappedBook);
		when(mapper.toDto(any(Book.class))).thenReturn(mappedDto);

		final var actual = service.create(expected);

		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testCreateAuthorNotFound() {
		final var authorId = 1L;
		final var dto = BookDto
				.builder()
				.authorId(authorId)
				.build();

		final var expected = ResourceNotFoundException.class;

		Assertions.assertThatThrownBy(() -> service.create(dto)).isInstanceOf(expected);
	}

	@Test
	void testUpdate() {
		final var id = 1L;
		final var authorId = 1L;
		final var expected = BookDto
				.builder()
				.id(id)
				.authorId(authorId)
				.build();
		final var book = Book
				.builder()
				.id(id)
				.build();
		final var author = Author
				.builder()
				.id(authorId)
				.build();

		when(repository.findById(id)).thenReturn(Optional.of(book));
		when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));
		doNothing().when(mapper).update(expected, author, book);
		when(repository.save(book)).thenReturn(book);
		when(mapper.toDto(book)).thenReturn(expected);

		final var actual = service.update(expected);

		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testUpdateBookNotFound() {
		final var id = 1L;
		final var dto = BookDto
				.builder()
				.id(id)
				.build();

		final var expected = ResourceNotFoundException.class;

		Assertions.assertThatThrownBy(() -> service.update(dto)).isInstanceOf(expected);
	}

	@Test
	void testUpdateAuthorNotFound() {
		final var id = 1L;
		final var authorId = 1L;
		final var dto = BookDto
				.builder()
				.id(id)
				.authorId(authorId)
				.build();

		final var expected = ResourceNotFoundException.class;

		Assertions.assertThatThrownBy(() -> service.update(dto)).isInstanceOf(expected);
	}

	@Test
	void testDelete() {
		final var expected = 1L;
		final var book = Book
				.builder()
				.id(expected)
				.build();

		when(repository.findById(expected)).thenReturn(Optional.of(book));

		final var actual = service.delete(expected);

		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testDeleteBookNotFound() {
		final var id = 1L;
		final var expected = ResourceNotFoundException.class;

		Assertions.assertThatThrownBy(() -> service.delete(id)).isInstanceOf(expected);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testFilter() {
		final var filter = BookFilter
				.builder()
				.name("Book 1")
				.build();
		final var book1 = Book
				.builder()
				.name("Book 1")
				.build();
		final var bookDto1 = BookDto
				.builder()
				.name("Book 1")
				.build();
		final var books = List.of(book1);
		final var expected = List.of(bookDto1);

		Specification<Book> bookSpecification = Specification.where(null);
		when(specification.filter(filter)).thenReturn(bookSpecification);
		when(repository.findAll(any(Specification.class))).thenReturn(books);
		when(mapper.toDto(book1)).thenReturn(bookDto1);

		final var actual = service.filter(filter);

		Assertions.assertThat(actual).isEqualTo(expected);
	}

}
