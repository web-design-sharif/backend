package com.shariff.backend.service;

import com.shariff.backend.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    public UserDTO signIn(UserDTO user) throws ResponseStatusException {
        // TODO: implement me
        return new UserDTO();
    }

    public UserDTO signUp(UserDTO user) throws ResponseStatusException {
        // TODO: implement me
        return new UserDTO();
    }
}
