package com.shariff.backend.model;
@Entity
@Table(name = "question")
public class Question {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    private String questionText;
    private String questionType;
    private boolean isRequired;
    private Date createdAt;
    private Date updatedAt;
}