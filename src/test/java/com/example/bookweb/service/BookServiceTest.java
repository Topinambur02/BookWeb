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
	private BookRepository repository;

	@Mock
	private AuthorRepository authorRepository;

	@Mock
	private BookMapper mapper;

	@Mock
	private BookSpecification specification;

	@InjectMocks
	private BookService service;

	@Test
	void testGetAll() {
		final var book1 = new Book(); 
        final var book2 = new Book(); 
        final var bookDto1 = new BookDto(); 
        final var bookDto2 = new BookDto();
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

		final var author = new Author();

		author.setId(id);

		final var mappedDto = new BookDto();

		mappedDto.setId(id);
		mappedDto.setAuthorId(authorId);

		final var mappedBook = new Book();

		mappedBook.setId(id);

		final var expected = new BookDto();

		expected.setId(id);
		expected.setAuthorId(id);

		when(authorRepository.findById(id)).thenReturn(Optional.of(author));
		when(repository.save(any(Book.class))).thenReturn(new Book());
		when(mapper.toBook(any(BookDto.class), any(Author.class))).thenReturn(mappedBook);
		when(mapper.toDto(any(Book.class))).thenReturn(mappedDto);
		
		final var actual = service.create(expected);

		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testCreateAuthorNotFound() {
		final var id = 1L;
		final var dto = new BookDto();

		dto.setAuthorId(id);

		final var expected = ResourceNotFoundException.class;

		Assertions.assertThatThrownBy(() -> service.create(dto)).isInstanceOf(expected);
	}

	@Test
	void testUpdate() {
		final var id = 1L;
		final var authorId = 1L;
		final var expected = new BookDto();

		expected.setId(id);
		expected.setAuthorId(authorId);

		final var book = new Book();

		book.setId(id);

		final var author = new Author();

		author.setId(authorId);

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
		final var dto = new BookDto();

		dto.setId(id);

		final var expected = ResourceNotFoundException.class;

		Assertions.assertThatThrownBy(() -> service.update(dto)).isInstanceOf(expected);
	}

	@Test
	void testUpdateAuthorNotFound() {
		final var id = 1L;
		final var authorId = 1L;
		final var dto = new BookDto();

		dto.setId(id);
		dto.setAuthorId(authorId);

		final var expected = ResourceNotFoundException.class;

		Assertions.assertThatThrownBy(() -> service.update(dto)).isInstanceOf(expected);
	}

	@Test
	void testDelete() {
		final var id = 1L;
		final var book = new Book();

		book.setId(id);

		final var expected = id;

		when(repository.findById(id)).thenReturn(Optional.of(book));

		final var actual = service.delete(id);

		Assertions.assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testDeleteBookNotFound() {
		final var id = 1L;
		final var expected = ResourceNotFoundException.class;

		Assertions.assertThatThrownBy(() -> service.delete(id)).isInstanceOf(expected);
	}

	@Test
	void testFilter() {
		final var filter = new BookFilter();

		filter.setName("Book 1");

        final var book1 = new Book();

		book1.setName("Book 1");

        final var book2 = new Book();

		book2.setName("Book 2");

        final var bookDto1 = new BookDto();
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
