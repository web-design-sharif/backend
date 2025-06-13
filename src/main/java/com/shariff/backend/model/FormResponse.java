package com.shariff.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;
import java.time.LocalDateTime;

@Entity
@Table(name = "form_response")
@Getter
@Setter
public class FormResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @ManyToOne
    @JoinColumn(name = "responder_id")
    private User responder;

    @CreationTimestamp
    @Column(name = "submitted_at", updatable = false)
    private LocalDateTime submittedAt;

    @OneToMany(mappedBy = "formResponse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Answer> answers;
}
