package com.shariff.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateFormResponseDTO {
    private int userId;
    private int formId;
    private List<FormResponseDTO> formResponses;
}