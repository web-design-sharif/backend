package com.shariff.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shariff.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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

    }
}
