package com.example.myapplication;

import java.util.List;

public class Quiz {
    private String id;
    private String name;
    private String description;
    private List<Question> questions;

    public Quiz(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}