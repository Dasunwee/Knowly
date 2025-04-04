package com.example.myapplication;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Question {
    private String id;
    private String quizId;
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    // Constructor for Firestore data
    public Question(String id, String quizId, String questionText, Map<String, String> options, String correctOption) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;

        // Convert options map to list (A,B,C,D order)
        this.options = Arrays.asList(
                options.get("A"),
                options.get("B"),
                options.get("C"),
                options.get("D")
        );

        // Convert correct option letter (A-D) to index (0-3)
        this.correctAnswerIndex = correctOption.charAt(0) - 'A';
    }

    // Constructor for direct values
    public Question(String id, String quizId, String questionText, List<String> options, int correctAnswerIndex) {
        this.id = id;
        this.quizId = quizId;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Getters
    public String getId() { return id; }
    public String getQuizId() { return quizId; }
    public String getText() { return questionText; }
    public List<String> getOptions() { return options; }
    public int getCorrectAnswerIndex() { return correctAnswerIndex; }

    // Helper method to get correct option text
    public String getCorrectOptionText() {
        return options.get(correctAnswerIndex);
    }

    // Helper method to get correct option letter (A-D)
    public String getCorrectOptionLetter() {
        return String.valueOf((char) ('A' + correctAnswerIndex));
    }

    // Convert to Map for Firestore
    public Map<String, Object> toFirestoreMap() {
        return Map.of(
                "question", questionText,
                "options", Map.of(
                        "A", options.get(0),
                        "B", options.get(1),
                        "C", options.get(2),
                        "D", options.get(3)
                ),
                "correctOption", getCorrectOptionLetter()
        );
    }
}