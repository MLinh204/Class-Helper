package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Game;
import com.example.Class_Helper.model.Question;
import com.example.Class_Helper.model.Quiz;
import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.repository.GameRepository;
import com.example.Class_Helper.repository.QuestionRepository;
import com.example.Class_Helper.repository.QuizRepository;
import com.example.Class_Helper.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private StudentRepository studentRepository;
    public Game createGame(Student player1, Student player2, Long quizId) {
        Game game = new Game();
        game.setCurrentPlayer(player1);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
        game.setQuizCategory(quiz);
        game.setBoard(new ArrayList<>(List.of("", "", "", "", "", "", "", "", "")));
        return gameRepository.save(game);
    }
    public Question getRandomQuestion(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        List<Question> questions = quiz.getQuestions();
        if (questions.isEmpty()) {
            throw new RuntimeException("No questions available for this quiz");
        }

        return questions.get(new Random().nextInt(questions.size()));
    }
    public boolean isCorrectAnswer(Question question, int answer){
        return question.getCorrectOptionIndex() == answer;
    }
    private boolean checkWinner(Game game) {
        List<String> board = game.getBoard();
        String symbol = game.getCurrentPlayer().equals(game.getPlayer1()) ? game.getPlayer2().getSymbol() : game.getPlayer1().getSymbol();

        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((board.get(i * 3).equals(symbol) && board.get(i * 3 + 1).equals(symbol) && board.get(i * 3 + 2).equals(symbol)) ||
                    (board.get(i).equals(symbol) && board.get(i + 3).equals(symbol) && board.get(i + 6).equals(symbol))) {
                return true;
            }
        }

        return (board.get(0).equals(symbol) && board.get(4).equals(symbol) && board.get(8).equals(symbol)) ||
                (board.get(2).equals(symbol) && board.get(4).equals(symbol) && board.get(6).equals(symbol));
    }
    public boolean isBoardGameFull(Game game){
        return !game.getBoard().contains("");
    }
    public void switchPlayer(Game game){
        game.setCurrentPlayer(game.getCurrentPlayer().equals(game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1());
    }
    public Game getGame(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }
    public Game playGame(Long gameId, int position, int answer, Long quizId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
        switchPlayer(game);
        Question question = getRandomQuestion(quizId);
        if (isCorrectAnswer(question, answer) && game.getBoard().get(position).isEmpty()) {
            List<String> updatedBoard = new ArrayList<>(game.getBoard());
            String symbol = game.getCurrentPlayer().equals(game.getPlayer1()) ? game.getPlayer2().getSymbol() : game.getPlayer1().getSymbol();
            updatedBoard.set(position, symbol);
            game.setBoard(updatedBoard);

            if (checkWinner(game)) {
                game.setWinner(game.getCurrentPlayer().equals(game.getPlayer1()) ? game.getPlayer2().getName() : game.getPlayer1().getName());
            } else if (isBoardGameFull(game)) {
                game.setWinner("Tie");
            }
        }
        return gameRepository.save(game);
    }
}
