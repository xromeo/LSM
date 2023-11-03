package com.rivera.learningmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class LearningLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Student student;

    @NotNull
    @Column(name = "date")
    private LocalDate logDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_category")
    private TaskCategory taskCategory;

    @Column(name = "task_description")
    private String taskDescription;

    @NotNull
    @PastOrPresent
    @Column(name = "time_spent")
    private LocalDateTime timeSpent;

    public LearningLog() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDateTime getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(LocalDateTime timeSpent) {
        this.timeSpent = timeSpent;
    }

    // Constructors, getters, and setters

    public LearningLog(
                       Student student,
                       LocalDate logDate,
                       TaskCategory taskCategory,
                       String taskDescription,
                       LocalDateTime timeSpent) {

        this.student = student;
        this.logDate = logDate;
        this.taskCategory = taskCategory;
        this.taskDescription = taskDescription;
        this.timeSpent = timeSpent;
    }
}

