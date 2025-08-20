package com.shariff.backend.service;

import com.shariff.backend.dto.*;
import com.shariff.backend.enums.QuestionType;
import com.shariff.backend.model.*;
import com.shariff.backend.repository.FormRepository;
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
public class FormService {
    private final FormRepository formRepository;
    private final UserRepository userRepository;

    public void publish(int formId, int userId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        if (form.getOwner().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to publish this form");
        }
        form.setPublished(!form.isPublished());
        formRepository.save(form);
    }

    public void create(CreateFormRequestDTO createFormRequestDTO, int userId) {
        // Fetch form owner from JWT token
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner user not found"));

        Form form = new Form();
        form.setOwner(owner);
        form.setTitle(createFormRequestDTO.getFormDTO().getTitle());
        form.setPublished(createFormRequestDTO.getFormDTO().isPublished());

        // Fetch submitters by email
        List<User> submitters = new ArrayList<>();
        if (createFormRequestDTO.getFormDTO().getSubmitters() != null) {
            for (UserDTO userDTO : createFormRequestDTO.getFormDTO().getSubmitters()) {
                User submitter = userRepository.findByEmail(userDTO.getEmail())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Submitter user not found"));
                submitters.add(submitter);
            }
        }
        form.setSubmitters(submitters);

        // Prepare questions and options manually
        List<Question> questions = new ArrayList<>();
        if (createFormRequestDTO.getFormDTO().getQuestion() != null) {
            for (QuestionDTO questionDTO : createFormRequestDTO.getFormDTO().getQuestion()) {
                Question question = new Question();
                question.setForm(form);
                question.setTitle(questionDTO.getTitle());
                question.setQuestionType(questionDTO.getQuestionType().name());
                question.setRequired(questionDTO.isRequired());

                List<QuestionOption> options = new ArrayList<>();
                if (questionDTO.getOptions() != null) {
                    for (OptionDTO optionDTO : questionDTO.getOptions()) {
                        QuestionOption option = new QuestionOption();
                        option.setQuestion(question);
                        option.setOptionText(String.valueOf(optionDTO.getOptionText()));
                        options.add(option);
                    }
                }
                question.setOptions(options);
                questions.add(question);
            }
        }
        form.setQuestions(questions);
        formRepository.save(form);
    }

    public void delete(int formId, int userId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        if (form.getOwner().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this form");
        }
        formRepository.delete(form);
    }

    public FormDTO getById(int formId, int userId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        if (form.getOwner().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this form");
        }

        return mapFormEntityToDto(form);
    }

    public List<FormDTO> getMyForms(int userId) {
        List<Form> forms = formRepository.findByOwner_Id(userId);

        return forms.stream()
                .map(this::mapFormEntityToDto)
                .collect(Collectors.toList());
    }

    public List<FormDTO> getPendingForms(int userId) {
        List<Form> forms = formRepository.findBySubmittersId(userId);

        List<Form> publishedForms = forms.stream()
                .filter(Form::isPublished)
                .toList();

        return publishedForms.stream()
                .map(this::mapFormEntityToDto)
                .collect(Collectors.toList());
    }

    // A private helper method to handle manual mapping
    private FormDTO mapFormEntityToDto(Form form) {
        FormDTO formDTO = new FormDTO();
        formDTO.setId(form.getId());
        formDTO.setOwnerId(form.getOwner().getId());
        formDTO.setTitle(form.getTitle());
        formDTO.setPublished(form.isPublished());
        formDTO.setCreatedAt(form.getCreatedAt());
        formDTO.setUpdatedAt(form.getUpdatedAt());

        formDTO.setSubmitters(form.getSubmitters().stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(null); // Do not expose password
            return userDTO;
        }).collect(Collectors.toList()));

        formDTO.setQuestion(form.getQuestions().stream().map(question -> {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setTitle(question.getTitle());
            questionDTO.setQuestionType(QuestionType.valueOf(question.getQuestionType()));
            questionDTO.setRequired(question.isRequired());
            questionDTO.setCreatedAt(question.getCreatedAt());
            questionDTO.setUpdatedAt(question.getUpdatedAt());

            questionDTO.setOptions(question.getOptions().stream().map(option -> {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setId(option.getId());
                optionDTO.setOptionText(option.getOptionText());
                return optionDTO;
            }).collect(Collectors.toList()));

            return questionDTO;
        }).collect(Collectors.toList()));

        return formDTO;
    }
}