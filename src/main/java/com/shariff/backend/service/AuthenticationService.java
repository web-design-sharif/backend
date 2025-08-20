package com.shariff.backend.service;

import com.shariff.backend.dto.AuthRequestDTO;
import com.shariff.backend.dto.UserDTO;
import com.shariff.backend.model.User;
import com.shariff.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    public UserDTO signUp(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public String signIn(AuthRequestDTO authRequestDTO) {
        return jwtService.generateToken(authRequestDTO.getEmail());
    }
}