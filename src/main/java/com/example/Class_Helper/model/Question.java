package com.example.Class_Helper.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @ElementCollection
    private List<String> options;

    @Column(nullable = true)
    private Integer correctOptionIndex;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Integer getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    public void setCorrectOptionIndex(Integer correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
