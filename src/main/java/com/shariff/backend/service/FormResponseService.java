package com.shariff.backend.service;

import com.shariff.backend.dto.*;
import com.shariff.backend.repository.FormRepository;
import com.shariff.backend.repository.FormResponseRepository;
import com.shariff.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.shariff.backend.model.*;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FormResponseService {
    private final FormResponseRepository formResponseRepository;
    private final FormRepository formRepository;
    private final UserRepository userRepository;

    public void submit(CreateFormResponseDTO formResponseDTO) throws ResponseStatusException {
        // Fetch form
        Form form = formRepository.findById(formResponseDTO.getFormId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        // Fetch responder
        User responder = userRepository.findById(formResponseDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check if responder is in submitters
        boolean isSubmitter = form.getSubmitters().stream()
                .anyMatch(user -> user.getId() == responder.getId());

        if (!isSubmitter) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not allowed to submit this form");
        }

        // Build FormResponse
        FormResponse formResponse = new FormResponse();
        formResponse.setForm(form);
        formResponse.setResponder(responder);

        List<Answer> answers = new ArrayList<>();
        if (formResponseDTO.getFormResponse().getAnswers() != null) {
            for (AnswerDTO answerDTO : formResponseDTO.getFormResponse().getAnswers()) {
                Answer answer = new Answer();
                answer.setFormResponse(formResponse);

                // Link question by ID only
                Question question = new Question();
                question.setId(answerDTO.getQuestionId());
                answer.setQuestion(question);

                answer.setAnswerText(answerDTO.getAnswerText());

                // Build answer options
                List<AnswerOption> answerOptions = new ArrayList<>();
                if (answerDTO.getAnswerOptions() != null) {
                    for (AnswerOptionDTO answerOptionDTO : answerDTO.getAnswerOptions()) {
                        AnswerOption answerOption = new AnswerOption();
                        answerOption.setAnswer(answer);

                        // Link option by ID only
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

        formResponse.setAnswers(answers);
        formResponseRepository.save(formResponse); // ✅ Cascade saves everything
    }


    public FormResponseDTO[] getAllResponses(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        // 1. Fetch the form
        Form form = formRepository.findById(userFormRequestDTO.getFormId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        // 2. Check ownership
        if (form.getOwner().getId() != userFormRequestDTO.getUserId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of this form");
        }

        // 3. Fetch all form responses for this form
        List<FormResponse> formResponses = formResponseRepository.findByForm_Id(form.getId());

        // 4. Map the responses to DTOs
        List<FormResponseDTO> formResponseDTOs = new ArrayList<>();

        for (FormResponse formResponse : formResponses) {
            FormResponseDTO responseDTO = new FormResponseDTO();
            responseDTO.setId(formResponse.getId());
            responseDTO.setFormId(formResponse.getForm().getId());
            responseDTO.setResponderId(formResponse.getResponder().getId());
            responseDTO.setSubmittedAt(formResponse.getSubmittedAt());

            // Fetch answers for this response
            List<AnswerDTO> answerDTOs = new ArrayList<>();
            for (Answer answer : formResponse.getAnswers()) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setId(answer.getId());
                answerDTO.setQuestionId(answer.getQuestion().getId());
                answerDTO.setAnswerText(answer.getAnswerText());
                answerDTO.setCreatedAt(answer.getCreatedAt());

                // Fetch answer options
                List<AnswerOptionDTO> answerOptionDTOs = new ArrayList<>();
                for (AnswerOption answerOption : answer.getAnswerOptions()) {
                    AnswerOptionDTO answerOptionDTO = new AnswerOptionDTO();
                    answerOptionDTO.setId(answerOption.getId());
                    answerOptionDTO.setOptionId(answerOption.getOption().getId());
                    answerOptionDTOs.add(answerOptionDTO);
                }
                answerDTO.setAnswerOptions(answerOptionDTOs.toArray(new AnswerOptionDTO[0]));
                answerDTOs.add(answerDTO);
            }
            responseDTO.setAnswers(answerDTOs.toArray(new AnswerDTO[0]));
            formResponseDTOs.add(responseDTO);
        }

        return formResponseDTOs.toArray(new FormResponseDTO[0]);
    }
}
