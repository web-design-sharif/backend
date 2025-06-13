package com.shariff.backend.service;

import com.shariff.backend.dto.UserDTO;
import com.shariff.backend.exception.InvalidCredentialsException;
import com.shariff.backend.exception.UserAlreadyExistsException;
import com.shariff.backend.model.User;
import com.shariff.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public UserDTO signUp(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists.");
        }

        User newUser = User.builder()
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();

        User savedUser = userRepository.save(newUser);

        return UserDTO.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .build();
    }

    public UserDTO signIn(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!userDTO.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}