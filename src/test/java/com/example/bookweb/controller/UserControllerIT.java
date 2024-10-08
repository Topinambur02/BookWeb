package com.example.bookweb.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.controller.UserController;
import com.example.dto.ConfirmRegistrationResponse;
import com.example.dto.SignInDto;
import com.example.dto.SignUpDto;
import com.example.dto.TokenResponse;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;
    
    @Test
    @WithMockUser
    public void testSignUp() throws Exception {
        
        final var dto = new SignUpDto();

        dto.setUsername("test");
        dto.setEmail("test");
        dto.setPassword("test");

        when(service.signUp(dto)).thenReturn(dto);

        mockMvc.perform(post("/users/signup").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(dto)));

    }

    @Test
    @WithMockUser
    public void testSignIn() throws Exception {

        final var dto = new SignInDto();

        dto.setUsername("test");
        dto.setPassword("test");

        final var response = new TokenResponse();

        when(service.signIn(dto)).thenReturn(response);

        mockMvc.perform(post("/users/signin").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

    @Test
    @WithMockUser
    public void testConfirmRegistration() throws Exception {

        final var generatedString = "test";

        final var response = new ConfirmRegistrationResponse();

        when(service.confirmRegistration(generatedString, null)).thenReturn(response);

        mockMvc.perform(post("/users/register/{generated-string}", generatedString))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

}
