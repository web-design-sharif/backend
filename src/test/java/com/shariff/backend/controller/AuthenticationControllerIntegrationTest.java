package com.shariff.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariff.backend.dto.AuthRequestDTO;
import com.shariff.backend.dto.UserDTO;
import com.shariff.backend.model.User;
import com.shariff.backend.repository.FormResponseRepository;
import com.shariff.backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormResponseRepository formResponseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @AfterEach
    public void tearDown() {
        formResponseRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void signUp_shouldCreateUserAndReturn201Created() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("testuser@example.com");
        userDTO.setPassword("securePassword123");

        mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"email\":\"testuser@example.com\"}"));
    }

    @Test
    public void signIn_shouldReturnJwtTokenForValidCredentials() throws Exception {

        User user = new User();
        user.setEmail("testuser@example.com");

        user.setPassword(passwordEncoder.encode("securePassword123"));
        userRepository.save(user);

        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setEmail("testuser@example.com");
        authRequestDTO.setPassword("securePassword123");

        mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.emptyOrNullString())));
    }
}