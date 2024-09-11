package com.example.Class_Helper.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student currentPlayer;

    @ManyToOne
    @JoinColumn(name = "current_question_id")
    private Question currentQuestion;

    @ManyToOne
    private Student player1;
    @ManyToOne
    private Student player2;
    @ElementCollection
    private List<String> board;
    @ManyToOne
    private Quiz quizCategory;

    private String winner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Student currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<String> getBoard() {
        return board;
    }

    public void setBoard(List<String> board) {
        this.board = board;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Student getPlayer1() {
        return player1;
    }

    public void setPlayer1(Student player1) {
        this.player1 = player1;
    }

    public Student getPlayer2() {
        return player2;
    }

    public void setPlayer2(Student player2) {
        this.player2 = player2;
    }

    public Quiz getQuizCategory() {
        return quizCategory;
    }

    public void setQuizCategory(Quiz quizCategory) {
        this.quizCategory = quizCategory;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }
}
