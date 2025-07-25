package com.shariff.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shariff.backend.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private int id;
    private String title;
    private QuestionType questionType;
    @JsonProperty("isRequired")
    private boolean required;
    private OptionDTO[] options;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
