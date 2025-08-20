package com.shariff.backend.service;

import com.shariff.backend.dto.AnswerDTO;
import com.shariff.backend.dto.AnswerOptionDTO;
import com.shariff.backend.dto.CreateFormResponseDTO;
import com.shariff.backend.dto.FormResponseDTO;
import com.shariff.backend.model.*;
import com.shariff.backend.repository.FormRepository;
import com.shariff.backend.repository.FormResponseRepository;
import com.shariff.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormResponseService {

    private final FormResponseRepository formResponseRepository;
    private final FormRepository formRepository;
    private final UserRepository userRepository;

    public void submit(CreateFormResponseDTO formResponseDTO, int userId) {
        // Fetch form
        Form form = formRepository.findById(formResponseDTO.getFormId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        // Check if the form is published
        if (!form.isPublished()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This form is not published and cannot be submitted.");
        }

        // Fetch responder by ID from JWT
        User responder = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check if responder is allowed to submit this form
        boolean isSubmitter = form.getSubmitters().stream()
                .anyMatch(user -> user.getId() == responder.getId());

        if (!isSubmitter) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not allowed to submit this form");
        }

        // Build FormResponse entity
        FormResponse formResponse = new FormResponse();
        formResponse.setForm(form);
        formResponse.setResponder(responder);

        // Build Answers and AnswerOptions manually, similar to original logic
        List<Answer> answers = new ArrayList<>();
        if (formResponseDTO.getFormResponses() != null && !formResponseDTO.getFormResponses().isEmpty()) {
            FormResponseDTO singleFormResponseDTO = formResponseDTO.getFormResponses().get(0);
            if (singleFormResponseDTO.getAnswers() != null) {
                for (AnswerDTO answerDTO : singleFormResponseDTO.getAnswers()) {
                    Answer answer = new Answer();
                    answer.setFormResponse(formResponse);

                    Question question = new Question();
                    question.setId(answerDTO.getQuestionId());
                    answer.setQuestion(question);

                    answer.setAnswerText(answerDTO.getAnswerText());

                    List<AnswerOption> answerOptions = new ArrayList<>();
                    if (answerDTO.getAnswerOptions() != null) {
                        for (AnswerOptionDTO answerOptionDTO : answerDTO.getAnswerOptions()) {
                            AnswerOption answerOption = new AnswerOption();
                            answerOption.setAnswer(answer);

                            QuestionOption questionOption = new QuestionOption();
                            questionOption.setId(answerOptionDTO.getOptionId());
                            answerOption.setOption(questionOption);

                            answerOptions.add(answerOption);
                        }
                    }
                    answer.setAnswerOptions(answerOptions);
                    answers.add(answer);
                }
            }
        }
        formResponse.setAnswers(answers);

        formResponseRepository.save(formResponse);
    }

    public List<FormResponseDTO> getAllResponses(int formId, int userId) {
        // Fetch the form and check ownership
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        if (form.getOwner().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this form");
        }

        List<FormResponse> formResponses = formResponseRepository.findByForm_Id(formId);

        // Manual mapping from Entity to DTO
        return formResponses.stream()
                .map(formResponse -> {
                    FormResponseDTO responseDTO = new FormResponseDTO();
                    responseDTO.setId(formResponse.getId());
                    responseDTO.setFormId(formResponse.getForm().getId());
                    responseDTO.setResponderId(formResponse.getResponder().getId());
                    responseDTO.setSubmittedAt(formResponse.getSubmittedAt());

                    List<AnswerDTO> answerDTOs = formResponse.getAnswers().stream()
                            .map(answer -> {
                                AnswerDTO answerDTO = new AnswerDTO();
                                answerDTO.setId(answer.getId());
                                answerDTO.setQuestionId(answer.getQuestion().getId());
                                answerDTO.setAnswerText(answer.getAnswerText());
                                answerDTO.setCreatedAt(answer.getCreatedAt());

                                List<AnswerOptionDTO> optionDTOs = answer.getAnswerOptions().stream()
                                        .map(option -> {
                                            AnswerOptionDTO optionDTO = new AnswerOptionDTO();
                                            optionDTO.setId(option.getId());
                                            optionDTO.setOptionId(option.getOption().getId());
                                            return optionDTO;
                                        }).collect(Collectors.toList());

                                answerDTO.setAnswerOptions(optionDTOs);
                                return answerDTO;
                            }).collect(Collectors.toList());

                    responseDTO.setAnswers(answerDTOs);
                    return responseDTO;
                }).collect(Collectors.toList());
    }
}