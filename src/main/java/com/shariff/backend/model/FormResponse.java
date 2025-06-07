package com.shariff.backend.model;
@Entity
@Table(name = "form_response")
public class FormResponse {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @ManyToOne
    @JoinColumn(name = "responder_id")
    private User responder;

    private Date submittedAt;
}