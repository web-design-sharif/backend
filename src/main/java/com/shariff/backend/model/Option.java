package com.shariff.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "option")
public class Option {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "option_text")
    private String optionText;
}
