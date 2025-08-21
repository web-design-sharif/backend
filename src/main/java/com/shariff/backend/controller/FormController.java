package com.shariff.backend.controller;

import com.shariff.backend.dto.CreateFormRequestDTO;
import com.shariff.backend.dto.FormDTO;
import com.shariff.backend.service.FormService;
import com.shariff.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forms")
@CrossOrigin(origins = "http://185.226.119.237:3000/")
public class FormController {

    private final FormService formService;
    private final UserService userService;

    private int getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userService.getUserIdByEmail(username);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateFormRequestDTO createFormRequestDTO) {
        int userId = getAuthenticatedUserId();
        formService.create(createFormRequestDTO, userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{formId}/publish")
    public ResponseEntity<Void> publish(@PathVariable int formId) {
        int userId = getAuthenticatedUserId();
        formService.publish(formId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{formId}")
    public ResponseEntity<Void> delete(@PathVariable int formId) {
        int userId = getAuthenticatedUserId();
        formService.delete(formId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{formId}")
    public ResponseEntity<FormDTO> getById(@PathVariable int formId) {
        int userId = getAuthenticatedUserId();
        FormDTO formDTO = formService.getById(formId, userId);
        return ResponseEntity.ok(formDTO);
    }

    @GetMapping
    public ResponseEntity<List<FormDTO>> getMyForms() {
        int userId = getAuthenticatedUserId();
        List<FormDTO> forms = formService.getMyForms(userId);
        return ResponseEntity.ok(forms);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FormDTO>> getPendingForms() {
        int userId = getAuthenticatedUserId();
        List<FormDTO> forms = formService.getPendingForms(userId);
        return ResponseEntity.ok(forms);
    }
}