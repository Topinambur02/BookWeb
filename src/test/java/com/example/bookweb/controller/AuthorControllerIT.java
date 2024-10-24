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
import com.example.dto.rest.AuthorDto;
import com.example.service.AuthorService;
import com.example.service.UserDetailsServiceImpl;
import com.example.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthorController.class)
class AuthorControllerIT {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private JwtUtils jwtUtils;
        @MockBean
        private AuthorService service;
        @MockBean
        private UserDetailsServiceImpl userDetailsService;

        @Test
        @WithMockUser
        void testGetAll() throws Exception {
                final var authors = List.of(
                                AuthorDto.builder().id(1L).name("Author 1").build(),
                                AuthorDto.builder().id(2L).name("Author 2").build());

                when(service.getAll()).thenReturn(authors);

                mockMvc.perform(get("/authors"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.length()").value(authors.size()))
                                .andExpect(jsonPath("$[0].id").value(1L))
                                .andExpect(jsonPath("$[0].name").value("Author 1"));
        }

        @Test
        @WithMockUser
        void testCreate() throws Exception {
                final var dto = AuthorDto
                                .builder()
                                .id(1L)
                                .name("Author test")
                                .build();

                when(service.create(dto)).thenReturn(dto);

                mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(dto)))
                                .andExpect(status().isOk())
                                .andExpect(content()
                                                .json(new ObjectMapper().writeValueAsString(dto)));
        }

}
