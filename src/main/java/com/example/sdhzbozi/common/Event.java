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

}
