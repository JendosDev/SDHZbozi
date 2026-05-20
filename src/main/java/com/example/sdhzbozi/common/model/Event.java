package com.example.sdhzbozi.common.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "event_date")
    private LocalDateTime date;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "created_by")
    private Long createdById;

    public Event() {
    }

    public Event(String title, String description, LocalDateTime date, Long departmentId, Long createdById) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.departmentId = departmentId;
        this.createdById = createdById;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }
}