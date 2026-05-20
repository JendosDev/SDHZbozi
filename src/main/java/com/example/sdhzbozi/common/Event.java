package com.example.sdhzbozi.common;

import jakarta.persistence.*;

@Entity
@Table("events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String name;

    @Column(name = "description")
    private String description;

    public Event() {
    }

    public Event(String author, String name, String description) {
        this.author = author;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
