package com.shariff.backend.controller;

import com.shariff.backend.dto.CreateFormResponseDTO;
import com.shariff.backend.dto.FormResponseDTO;
import com.shariff.backend.dto.UserFormRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/response")
public class FormResponseController {
    @PostMapping("/submit")
    public void submit(@RequestBody CreateFormResponseDTO formResponseDTO) {

    }

    @GetMapping("/all-responses")
    public FormResponseDTO[] getAllResponses(@RequestBody UserFormRequestDTO userFormRequestDTO) {
        return new FormResponseDTO[0];
    }
}
