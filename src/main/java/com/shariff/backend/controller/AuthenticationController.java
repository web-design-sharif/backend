package com.shariff.backend.controller;

import com.shariff.backend.dto.AuthenticationRequest;
import com.shariff.backend.dto.AuthenticationResponse;
import com.shariff.backend.dto.RegisterRequest;
import com.shariff.backend.routes.Routes;
import com.shariff.backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.Auth.BASE)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(Routes.Auth.SIGN_UP)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping(Routes.Auth.SIGN_IN)
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
} 