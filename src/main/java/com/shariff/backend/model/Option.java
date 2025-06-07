package com.shariff.backend.model;
@Entity
@Table(name = "option")
public class Option {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String optionText;
}