package com.shariff.backend.controller;

import com.shariff.backend.dto.CreateFormRequestDTO;
import com.shariff.backend.dto.FormDTO;
import com.shariff.backend.dto.UserFormRequestDTO;
import com.shariff.backend.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/form")
@CrossOrigin(origins = "http://localhost:3000")
public class FormController {
    private final FormService formService;

    @PostMapping("/publish")
    public void publish(@RequestBody UserFormRequestDTO userFormRequestDTO) {
        formService.publish(userFormRequestDTO);
    }

    @PostMapping("/create")
    public void create(@RequestBody CreateFormRequestDTO createFormRequestDTO) {
        formService.create(createFormRequestDTO);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody UserFormRequestDTO userFormRequestDTO) {
        formService.delete(userFormRequestDTO);
    }

    @GetMapping("/id")
    public FormDTO getById(@RequestBody UserFormRequestDTO userFormRequestDTO) {
        return formService.getById(userFormRequestDTO);
    }

    @GetMapping("/my-forms")
    public FormDTO[] getMyForms(@RequestParam int userId) {
        return formService.getMyForms(userId);
    }

    @GetMapping("/pending-forms")
    public FormDTO[] getPendingForms(@RequestParam int userId) {
        return formService.getPendingForms(userId);
    }
}
