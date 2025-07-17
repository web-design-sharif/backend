package com.shariff.backend.controller;

import com.shariff.backend.dto.UserDTO;
import com.shariff.backend.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://188.121.110.51:3000")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> signUp(@Valid @RequestBody UserDTO userDTO) {
        UserDTO registeredUser = authenticationService.signUp(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserDTO> signIn(@Valid @RequestBody UserDTO userDTO) {
        UserDTO authenticatedUser = authenticationService.signIn(userDTO);
        return ResponseEntity.ok(authenticatedUser);
    }
}