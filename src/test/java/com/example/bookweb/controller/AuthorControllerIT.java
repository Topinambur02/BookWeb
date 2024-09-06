package com.example.bookweb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.controller.AuthorController;
import com.example.dto.AuthorDto;
import com.example.service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService service;

    @Test
    @WithMockUser
    public void testGetAll() throws Exception {
        final var authors = List.of(
                new AuthorDto(),
                new AuthorDto());

        authors.get(0).setId(1L);
        authors.get(0).setName("Author 1");
        authors.get(1).setId(2L);
        authors.get(1).setName("Author 2");

        when(service.getAll()).thenReturn(authors);

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(authors.size()))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Author 1"));
    }

    @Test
    @WithMockUser
    public void testCreate() throws Exception {

        final var author = new AuthorDto();

        author.setId(1L);
        author.setName("Author test");

        when(service.create(author)).thenReturn(author);

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(author)));
    }

}
