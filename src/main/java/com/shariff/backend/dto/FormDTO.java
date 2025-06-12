package com.shariff.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormDTO {
    private int id;
    private int ownerId;
    private String title;
    private boolean isPublished;
    private UserDTO[] submitters;
    private QuestionDTO[] question;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
