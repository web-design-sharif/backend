package com.shariff.backend.model;
@Entity
@Table(name = "form")
public class Form {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}