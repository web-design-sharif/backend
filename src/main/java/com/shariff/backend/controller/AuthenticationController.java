package com.shariff.backend.controller;

import com.shariff.backend.dto.UserDTO;
import com.shariff.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-in")
    public UserDTO signIn(@RequestBody UserDTO userDTO) {
        return authenticationService.signIn(userDTO);
    }

    @PostMapping("/sign-up")
    public UserDTO signUp(@RequestBody UserDTO userDTO) {
        return authenticationService.signUp(userDTO);
    }
}
