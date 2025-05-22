package com.shariff.backend.controller;

import com.shariff.backend.dto.SignInRequest;
import com.shariff.backend.dto.SignUpRequest;
import com.shariff.backend.model.User;
import com.shariff.backend.routes.Routes;
import com.shariff.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.Auth.BASE)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(Routes.Auth.SIGN_UP)
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        User user = authService.signUp(signUpRequest);
        return ResponseEntity.ok(user);
    }

    @PostMapping(Routes.Auth.SIGN_IN)
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
} 