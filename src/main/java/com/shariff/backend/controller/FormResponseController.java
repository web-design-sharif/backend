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
@CrossOrigin(origins = "http://188.121.110.51:3000")
public class FormResponseController {
    private final FormResponseService formResponseService;

    @PostMapping("/submit")
    public void submit(@RequestBody CreateFormResponseDTO formResponseDTO) {
        formResponseService.submit(formResponseDTO);
    }

    @GetMapping("/all-responses")
    public FormResponseDTO[] getAllResponses(@RequestParam int userId, @RequestParam int formId) {
        UserFormRequestDTO userFormRequestDTO = new UserFormRequestDTO(formId, userId);
        return formResponseService.getAllResponses(userFormRequestDTO);
    }
}
