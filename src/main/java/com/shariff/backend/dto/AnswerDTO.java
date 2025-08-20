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
public class AnswerDTO {
    private int id;
    private int questionId;
    private String answerText;
    private List<AnswerOptionDTO> answerOptions;
    private LocalDateTime createdAt;
}