package com.shariff.backend.model;
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "form_response_id")
    private FormResponse formResponse;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String answerText;
    private Date createdAt;
}