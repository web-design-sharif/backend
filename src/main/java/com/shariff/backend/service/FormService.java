package com.shariff.backend.service;

import com.shariff.backend.dto.*;
import com.shariff.backend.model.*;
import com.shariff.backend.repository.FormRepository;
import com.shariff.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final UserRepository userRepository;

    public void publish(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        // TODO: implement me
    }

//    public void create(CreateFormRequestDTO createFormRequestDTO) throws ResponseStatusException {
//        // TODO: implement me
//    }


    public void create(CreateFormRequestDTO createFormRequestDTO) throws ResponseStatusException {
        // Fetch form owner
        User owner = userRepository.findById((long) createFormRequestDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner user not found"));

        Form form = new Form();
        form.setOwner(owner);
        form.setTitle(createFormRequestDTO.getFormDTO().getTitle());
        form.setPublished(createFormRequestDTO.getFormDTO().isPublished());

        // Fetch submitters
        List<User> submitters = new java.util.ArrayList<>();
        if (createFormRequestDTO.getFormDTO().getSubmitters() != null) {
            for (UserDTO userDTO : createFormRequestDTO.getFormDTO().getSubmitters()) {
                User submitter = userRepository.findById((long) userDTO.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Submitter user not found"));
                submitters.add(submitter);
            }
        }
        form.setSubmitters(submitters);

        // Prepare questions
        List<Question> questions = new java.util.ArrayList<>();
        if (createFormRequestDTO.getFormDTO().getQuestion() != null) {
            for (QuestionDTO questionDTO : createFormRequestDTO.getFormDTO().getQuestion()) {
                Question question = new Question();
                question.setForm(form);
                question.setTitle(questionDTO.getTitle());
                question.setQuestionType(questionDTO.getQuestionType().name());
                question.setRequired(questionDTO.isRequired());

                List<QuestionOption> options = new java.util.ArrayList<>();
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

    public void delete(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        // TODO: implement me
    }

    public FormDTO getById(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        // TODO: implement me
        return new FormDTO();
    }

    public FormDTO[] getMyForms(int userId) throws ResponseStatusException {
        // TODO: implement me
        return new FormDTO[0];
    }

    public FormDTO[] getPendingForms(int userId) throws ResponseStatusException {
        // TODO: implement me
        return new FormDTO[0];
    }
}
