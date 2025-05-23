package com.shariff.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariff.backend.dto.AuthenticationRequest;
import com.shariff.backend.dto.RegisterRequest;
import com.shariff.backend.repository.UserRepository;
import com.shariff.backend.routes.Routes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PASSWORD = "Test123!";
    private static final String TEST_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void signUpSuccess() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .username(TEST_USERNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .name("Test")
                .lastname("User")
                .build();

        mockMvc.perform(post(Routes.Auth.BASE + Routes.Auth.SIGN_UP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.token").isString());

        // Verify user exists
        mockMvc.perform(post(Routes.Auth.BASE + Routes.Auth.SIGN_IN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthenticationRequest.builder()
                        .username(TEST_USERNAME)
                        .password(TEST_PASSWORD)
                        .build())))
                .andExpect(status().isOk());
    }

    @Test
    void signUpDuplicateUsername() throws Exception {
        // First registration
        RegisterRequest request = RegisterRequest.builder()
                .username(TEST_USERNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .name("Test")
                .lastname("User")
                .build();

        mockMvc.perform(post(Routes.Auth.BASE + Routes.Auth.SIGN_UP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Second registration with same username
        RegisterRequest duplicateRequest = RegisterRequest.builder()
                .username(TEST_USERNAME)
                .email("another@example.com")
                .password(TEST_PASSWORD)
                .name("Test")
                .lastname("User")
                .build();

        mockMvc.perform(post(Routes.Auth.BASE + Routes.Auth.SIGN_UP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(duplicateRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("Username already exists")));
    }

    @Test
    void signInSuccess() throws Exception {
        // First register a user
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(TEST_USERNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .name("Test")
                .lastname("User")
                .build();

        mockMvc.perform(post(Routes.Auth.BASE + Routes.Auth.SIGN_UP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        // Then try to sign in
        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .build();

        mockMvc.perform(post(Routes.Auth.BASE + Routes.Auth.SIGN_IN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.token").isString());
    }

    @Test
    void getMeSuccess() throws Exception {
        // First register a user and get token
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username(TEST_USERNAME)
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .name("Test")
                .lastname("User")
                .build();

        MvcResult result = mockMvc.perform(post(Routes.Auth.BASE + Routes.Auth.SIGN_UP)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String token = objectMapper.readTree(result.getResponse().getContentAsString())
                .get("token").asText();

        // Then try to access /me endpoint
        mockMvc.perform(get(Routes.Users.BASE + Routes.Users.ME)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(TEST_USERNAME)))
                .andExpect(jsonPath("$.email", is(TEST_EMAIL)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.lastname", is("User")))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void getMeUnauthorized() throws Exception {
        mockMvc.perform(get(Routes.Users.BASE + Routes.Users.ME))
                .andExpect(status().isUnauthorized());
    }
} 