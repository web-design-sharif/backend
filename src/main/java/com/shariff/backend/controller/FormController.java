package com.shariff.backend.controller;

import com.shariff.backend.dto.CreateFormRequestDTO;
import com.shariff.backend.dto.FormDTO;
import com.shariff.backend.dto.UserFormRequestDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
public class FormController {

    @PostMapping("/publish")
    public void publish(@RequestBody UserFormRequestDTO userFormRequestDTO) {

    }

    @PostMapping("/create")
    public void create(@RequestBody CreateFormRequestDTO createFormRequestDTO) {

    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody UserFormRequestDTO userFormRequestDTO) {

    }

    @GetMapping("/id")
    public FormDTO getById(@RequestBody UserFormRequestDTO userFormRequestDTO) {
        return new FormDTO();
    }

    @GetMapping("/my-forms")
    public FormDTO[] getMyForms(@RequestBody int userId) {
        return new FormDTO[0];
    }

    @GetMapping("/pending-forms")
    public FormDTO[] getPendingForms(@RequestBody int userId) {
        return new FormDTO[0];
    }
}
