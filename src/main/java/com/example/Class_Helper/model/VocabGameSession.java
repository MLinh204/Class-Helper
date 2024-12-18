package com.example.Class_Helper.model;

import java.util.List;

public class VocabGameSession {
    private String gameId; // Unique identifier for the session
    private Long studentId;
    private Long vocabGroupId;
    private List<Vocab> remainingVocabs; // List of vocabs to be used in the game
    private int totalPoints;
    private int correctAnswers;
    private int incorrectAnswers;


    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getVocabGroupId() {
        return vocabGroupId;
    }

    public void setVocabGroupId(Long vocabGroupId) {
        this.vocabGroupId = vocabGroupId;
    }

    public List<Vocab> getRemainingVocabs() {
        return remainingVocabs;
    }

    public void setRemainingVocabs(List<Vocab> remainingVocabs) {
        this.remainingVocabs = remainingVocabs;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(int incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
}
