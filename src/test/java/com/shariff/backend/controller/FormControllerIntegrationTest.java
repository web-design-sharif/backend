package com.shariff.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariff.backend.dto.AuthRequestDTO;
import com.shariff.backend.dto.CreateFormRequestDTO;
import com.shariff.backend.dto.FormDTO;
import com.shariff.backend.model.User;
import com.shariff.backend.repository.FormRepository;
import com.shariff.backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FormControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String jwtToken;
    private int testFormId;

    @BeforeEach
    void setUp() throws Exception {
        // 1. Register and sign in a test user to get a JWT token
        User user = new User();
        user.setEmail("formuser@example.com");
        user.setPassword(passwordEncoder.encode("securePassword123"));
        userRepository.save(user);

        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setEmail("formuser@example.com");
        authRequestDTO.setPassword("securePassword123");

        this.jwtToken = mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        FormDTO formDTO = new FormDTO();
        formDTO.setTitle("Test Form");
        formDTO.setPublished(false);

        formDTO.setSubmitters(new ArrayList<>());

        CreateFormRequestDTO createFormRequestDTO = new CreateFormRequestDTO(formDTO);


        mockMvc.perform(post("/forms")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createFormRequestDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        testFormId = formRepository.findAll().get(0).getId();
    }

    @AfterEach
    void tearDown() {
        formRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getById_shouldReturnFormForAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/forms/{formId}", testFormId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }

    @Test
    void getById_shouldReturn403ForUnauthorizedUser() throws Exception {
        // Create another user
        User otherUser = new User();
        otherUser.setEmail("otheruser@example.com");
        otherUser.setPassword(passwordEncoder.encode("securePassword123"));
        userRepository.save(otherUser);

        // Sign in with the other user
        AuthRequestDTO authRequestDTO = new AuthRequestDTO();
        authRequestDTO.setEmail("otheruser@example.com");
        authRequestDTO.setPassword("securePassword123");

        String otherUserToken = mockMvc.perform(post("/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // Try to access the form of the first user with the second user's token
        mockMvc.perform(get("/forms/{formId}", testFormId)
                        .header("Authorization", "Bearer " + otherUserToken))
                .andExpect(status().isForbidden());
    }

    @Test
    void delete_shouldRemoveFormForAuthenticatedUser() throws Exception {
        mockMvc.perform(delete("/forms/{formId}", testFormId)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void getMyForms_shouldReturnFormsForAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/forms")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk());
    }
}