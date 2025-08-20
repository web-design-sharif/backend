package com.shariff.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormResponseDTO {
    private int id;
    private int formId;
    private int responderId;
    private List<AnswerDTO> answers;
    private LocalDateTime submittedAt;
}