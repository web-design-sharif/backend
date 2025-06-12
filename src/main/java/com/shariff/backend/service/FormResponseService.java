package com.shariff.backend.service;

import com.shariff.backend.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FormResponseService {
    public void submit(CreateFormResponseDTO formResponseDTO) throws ResponseStatusException {
        // TODO: implement me
    }

    public FormResponseDTO[] getAllResponses(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        // TODO: implement me
        return new FormResponseDTO[0];
    }
}
