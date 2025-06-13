package com.shariff.backend.controller;

import com.shariff.backend.dto.CreateFormResponseDTO;
import com.shariff.backend.dto.FormResponseDTO;
import com.shariff.backend.dto.UserFormRequestDTO;
import com.shariff.backend.service.FormResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
@CrossOrigin(origins = "http://localhost:3000")
public class FormResponseController {
    private final FormResponseService formResponseService;

    @PostMapping("/submit")
    public void submit(@RequestBody CreateFormResponseDTO formResponseDTO) {
        formResponseService.submit(formResponseDTO);
    }

    @GetMapping("/all-responses")
    public FormResponseDTO[] getAllResponses(@RequestBody UserFormRequestDTO userFormRequestDTO) {
        return formResponseService.getAllResponses(userFormRequestDTO);
    }
}
