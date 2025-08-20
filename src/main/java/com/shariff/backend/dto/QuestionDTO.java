package com.shariff.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shariff.backend.enums.QuestionType;
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
public class QuestionDTO {
    private int id;
    private String title;
    private QuestionType questionType;
    @JsonProperty("isRequired")
    private boolean required;
    private List<OptionDTO> options;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}