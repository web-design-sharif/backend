package com.shariff.backend.controller;

import com.shariff.backend.model.User;
import com.shariff.backend.routes.Routes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.Users.BASE)
public class UserController {

    @GetMapping(Routes.Users.ME)
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return user;
    }
} 