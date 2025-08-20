package com.shariff.backend.controller;

import com.shariff.backend.dto.CreateFormResponseDTO;
import com.shariff.backend.dto.FormResponseDTO;
import com.shariff.backend.service.FormResponseService;
import com.shariff.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
@CrossOrigin(origins = "http://188.121.110.51:3000")
public class FormResponseController {
    private final FormResponseService formResponseService;
    private final UserService userService;

    private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userService.getUserIdByEmail(username);
    }

    @PostMapping("/submit")
    public void submit(@RequestBody CreateFormResponseDTO formResponseDTO) {
        formResponseService.submit(formResponseDTO, getAuthenticatedUserId());
    }

    @GetMapping("/{formId}/responses")
    public ResponseEntity<List<FormResponseDTO>> getAllResponses(@PathVariable int formId) {
        int userId = getAuthenticatedUserId();
        List<FormResponseDTO> responses = formResponseService.getAllResponses(formId, userId);
        return ResponseEntity.ok(responses);
    }
}