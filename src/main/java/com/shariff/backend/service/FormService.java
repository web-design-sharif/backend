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

import java.util.List;


@Service
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final UserRepository userRepository;

    public void publish(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        int formId = userFormRequestDTO.getFormId();
        int userId = userFormRequestDTO.getUserId();

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        if (form.getOwner().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this form");
        }
        form.setPublished(true);
        formRepository.save(form);
    }

    public void create(CreateFormRequestDTO createFormRequestDTO) throws ResponseStatusException {
        // Fetch form owner
        User owner = userRepository.findById(createFormRequestDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner user not found"));

        Form form = new Form();
        form.setOwner(owner);
        form.setTitle(createFormRequestDTO.getFormDTO().getTitle());
        form.setPublished(createFormRequestDTO.getFormDTO().isPublished());

        // Fetch submitters
        List<User> submitters = new java.util.ArrayList<>();
        if (createFormRequestDTO.getFormDTO().getSubmitters() != null) {
            for (UserDTO userDTO : createFormRequestDTO.getFormDTO().getSubmitters()) {
                User submitter = userRepository.findById(userDTO.getId())
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
        int formId = userFormRequestDTO.getFormId();
        int userId = userFormRequestDTO.getUserId();

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        if (form.getOwner().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this form");
        }
        formRepository.delete(form);
    }

    public FormDTO getById(UserFormRequestDTO userFormRequestDTO) throws ResponseStatusException {
        int formId = userFormRequestDTO.getFormId();
        int userId = userFormRequestDTO.getUserId();

        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Form not found"));

        if (form.getOwner().getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this form");
        }

        // Map Form entity to FormDTO
        FormDTO formDTO = new FormDTO();
        formDTO.setId(form.getId());
        formDTO.setOwnerId(form.getOwner().getId());
        formDTO.setTitle(form.getTitle());
        formDTO.setPublished(form.isPublished());
        formDTO.setCreatedAt(form.getCreatedAt());
        formDTO.setUpdatedAt(form.getUpdatedAt());

        // Map Submitters
        List<UserDTO> submittersDTO = form.getSubmitters().stream().map(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword()); // Optional: You may want to hide password
            return userDTO;
        }).toList();

        formDTO.setSubmitters(submittersDTO.toArray(new UserDTO[0]));

        // Map Questions
        List<QuestionDTO> questionDTOList = form.getQuestions().stream().map(question -> {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setTitle(question.getTitle());
            questionDTO.setQuestionType(QuestionType.valueOf(question.getQuestionType()));
            questionDTO.setRequired(question.isRequired());
            questionDTO.setCreatedAt(question.getCreatedAt());
            questionDTO.setUpdatedAt(question.getUpdatedAt());

            // Map Options
            List<OptionDTO> optionDTOList = question.getOptions().stream().map(option -> {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setId(option.getId());
                optionDTO.setOptionText(option.getOptionText());
                return optionDTO;
            }).toList();

            questionDTO.setOptions(optionDTOList.toArray(new OptionDTO[0]));

            return questionDTO;
        }).toList();

        formDTO.setQuestion(questionDTOList.toArray(new QuestionDTO[0]));

        return formDTO;
    }


    public FormDTO[] getMyForms(int userId) throws ResponseStatusException {
        // Retrieve all forms owned by this user
        List<Form> forms = formRepository.findByOwner_Id(userId);

        if (forms.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No forms found for this user");
        }

        List<FormDTO> formDTOList = forms.stream().map(form -> {
            // Map basic form fields
            FormDTO formDTO = new FormDTO();
            formDTO.setId(form.getId());
            formDTO.setOwnerId(form.getOwner().getId());
            formDTO.setTitle(form.getTitle());
            formDTO.setPublished(form.isPublished());
            formDTO.setCreatedAt(form.getCreatedAt());
            formDTO.setUpdatedAt(form.getUpdatedAt());

            // Map submitters
            List<UserDTO> submittersDTO = form.getSubmitters().stream().map(user -> {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setEmail(user.getEmail());
                userDTO.setPassword(user.getPassword()); // Optional: Can exclude for security
                return userDTO;
            }).toList();

            formDTO.setSubmitters(submittersDTO.toArray(new UserDTO[0]));

            // Map questions
            List<QuestionDTO> questionDTOList = form.getQuestions().stream().map(question -> {
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setId(question.getId());
                questionDTO.setTitle(question.getTitle());
                questionDTO.setQuestionType(QuestionType.valueOf(question.getQuestionType()));
                questionDTO.setRequired(question.isRequired());
                questionDTO.setCreatedAt(question.getCreatedAt());
                questionDTO.setUpdatedAt(question.getUpdatedAt());

                // Map options
                List<OptionDTO> optionDTOList = question.getOptions().stream().map(option -> {
                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setId(option.getId());
                    optionDTO.setOptionText(option.getOptionText());
                    return optionDTO;
                }).toList();

                questionDTO.setOptions(optionDTOList.toArray(new OptionDTO[0]));

                return questionDTO;
            }).toList();

            formDTO.setQuestion(questionDTOList.toArray(new QuestionDTO[0]));

            return formDTO;
        }).toList();

        return formDTOList.toArray(new FormDTO[0]);
    }


    public FormDTO[] getPendingForms(int userId) throws ResponseStatusException {
        // Retrieve all forms where the user is a submitter
        List<Form> forms = formRepository.findBySubmittersId(userId);

        // Filter only published forms
        forms = forms.stream()
                .filter(Form::isPublished)
                .toList();

        if (forms.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pending forms found for this user");
        }

        List<FormDTO> formDTOList = forms.stream().map(form -> {
            // Map form details
            FormDTO formDTO = new FormDTO();
            formDTO.setId(form.getId());
            formDTO.setOwnerId(form.getOwner().getId());
            formDTO.setTitle(form.getTitle());
            formDTO.setPublished(form.isPublished());
            formDTO.setCreatedAt(form.getCreatedAt());
            formDTO.setUpdatedAt(form.getUpdatedAt());

            // Map submitters
            List<UserDTO> submittersDTO = form.getSubmitters().stream().map(user -> {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setEmail(user.getEmail());
                return userDTO;
            }).toList();

            formDTO.setSubmitters(submittersDTO.toArray(new UserDTO[0]));

            // Map questions
            List<QuestionDTO> questionDTOList = form.getQuestions().stream().map(question -> {
                QuestionDTO questionDTO = new QuestionDTO();
                questionDTO.setId(question.getId());
                questionDTO.setTitle(question.getTitle());
                questionDTO.setQuestionType(QuestionType.valueOf(question.getQuestionType()));
                questionDTO.setRequired(question.isRequired());
                questionDTO.setCreatedAt(question.getCreatedAt());
                questionDTO.setUpdatedAt(question.getUpdatedAt());

                // Map options
                List<OptionDTO> optionDTOList = question.getOptions().stream().map(option -> {
                    OptionDTO optionDTO = new OptionDTO();
                    optionDTO.setId(option.getId());
                    optionDTO.setOptionText(option.getOptionText());
                    return optionDTO;
                }).toList();

                questionDTO.setOptions(optionDTOList.toArray(new OptionDTO[0]));

                return questionDTO;
            }).toList();

            formDTO.setQuestion(questionDTOList.toArray(new QuestionDTO[0]));

            return formDTO;
        }).toList();

        return formDTOList.toArray(new FormDTO[0]);
    }

}
