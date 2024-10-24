package com.example.bookweb.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.controller.BookController;
import com.example.dto.rest.BookDto;
import com.example.dto.rest.BookFilterDto;
import com.example.service.BookService;
import com.example.service.UserDetailsServiceImpl;
import com.example.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(BookController.class)
class BookControllerIT {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private JwtUtils jwtUtils;
        @MockBean
        private BookService service;
        @MockBean
        private UserDetailsServiceImpl userDetailsService;

        @Test
        @WithMockUser
        void testGetAll() throws Exception {
                final var book1 = BookDto
                                .builder()
                                .build();
                final var book2 = BookDto
                                .builder()
                                .build();
                final var expected = List.of(book1, book2);

                when(service.getAll()).thenReturn(expected);

                mockMvc.perform(get("/books"))
                                .andExpect(status().isOk())
                                .andExpect(content().json(new ObjectMapper().writeValueAsString(expected)));
        }

        @Test
        @WithMockUser
        void testCreate() throws Exception {
                final var dto = BookDto
                                .builder()
                                .id(1L)
                                .name("test")
                                .build();

                when(service.create(dto)).thenReturn(dto);

                mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(dto)))
                                .andExpect(status().isOk())
                                .andExpect(content().json(new ObjectMapper().writeValueAsString(dto)));
        }

        @Test
        @WithMockUser
        void testDelete() throws Exception {
                final var bookId = 1L;

                when(service.delete(bookId)).thenReturn(bookId);

                mockMvc.perform(delete("/books/{id}", bookId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").value(bookId));
        }

        @Test
        @WithMockUser
        void testFilter() throws Exception {
                final var filter = BookFilterDto.builder().build();
                final var book1 = BookDto.builder().build();
                final var book2 = BookDto.builder().build();
                final var expected = List.of(book1, book2);

                when(service.filter(filter)).thenReturn(expected);

                mockMvc.perform(get("/books/filter").contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(filter)))
                                .andExpect(status().isOk())
                                .andExpect(content().json(new ObjectMapper().writeValueAsString(expected)));
        }

        @Test
        @WithMockUser
        void testUpdate() throws Exception {
                final var bookId = 1L;
                final var dto = BookDto
                                .builder()
                                .id(bookId)
                                .name("test")
                                .build();

                when(service.create(dto)).thenReturn(dto);

                mockMvc.perform(post("/books").contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(dto)))
                                .andExpect(status().isOk())
                                .andExpect(content().json(new ObjectMapper().writeValueAsString(dto)));
        }

}
