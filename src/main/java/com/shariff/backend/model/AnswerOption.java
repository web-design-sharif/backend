package com.shariff.backend.model;
@Entity
@Table(name = "answer_option")
public class AnswerOption {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;
}
