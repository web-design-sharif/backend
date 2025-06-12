package com.shariff.backend.service;

import com.shariff.backend.dto.CreateFormRequestDTO;
import com.shariff.backend.dto.FormDTO;
import com.shariff.backend.dto.UserFormRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FormService {
    public void publish(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        // TODO: implement me
    }

    public void create(CreateFormRequestDTO createFormRequestDTO) throws ResponseStatusException {
        // TODO: implement me
    }

    public void delete(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        // TODO: implement me
    }

    public FormDTO getById(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        // TODO: implement me
        return new FormDTO();
    }

    public FormDTO[] getMyForms(int userId) throws ResponseStatusException {
        // TODO: implement me
        return new FormDTO[0];
    }

    public FormDTO[] getPendingForms(int userId) throws ResponseStatusException {
        // TODO: implement me
        return new FormDTO[0];
    }
}
