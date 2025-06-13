package com.shariff.backend.service;

import com.shariff.backend.dto.*;
import com.shariff.backend.repository.FormResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.shariff.backend.model.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FormResponseService {
    private final FormResponseRepository formResponseRepository;
    public void submit(CreateFormResponseDTO formResponseDTO) throws ResponseStatusException {
        // TODO: implement me
    }

    public FormResponseDTO[] getAllResponses(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
//        List<FormResponse> formResponses = formResponseRepository.findByFormIdAndResponderId(
//                userFormRequestDTO.getFormId(),
//                userFormRequestDTO.getUserId()
//        );
//
//        if (formResponses.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No responses found for the provided formId and userId.");
//        }
////       Check if
//        FormResponseDTO[] responseDTOs = formResponses.stream()
//                .map(formResponse -> FormResponseDTO.builder()
//                        .id(formResponse.getId())
//                        .formId(formResponse.getForm().getId())
//                        .responderId(formResponse.getResponder().getId())
//                        .submittedAt(formResponse.getSubmittedAt())
//                        .answers(formResponse.getAnswers().stream()
//                                .map(answer -> AnswerDTO.builder()
//                                        .id(answer.getId())
//                                        .questionId(answer.getQuestion().getId())
//                                        .answerText(answer.getAnswerText())
//                                        .createdAt(answer.getCreatedAt())
//                                        .answerOptions(answer.getAnswerOptions().stream()
//                                                .map(option -> AnswerOptionDTO.builder()
//                                                        .id(option.getId())
//                                                        .optionId(option.getOption().getId())
//                                                        .build())
//                                                .toArray(AnswerOptionDTO[]::new))
//                                        .build())
//                                .toArray(AnswerDTO[]::new))
//                        .build())
//                .toArray(FormResponseDTO[]::new);
//
//        return responseDTOs;
   return null; }
}
