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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
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
    public Question getRandomQuestion(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Quiz quiz = game.getQuizCategory();
        List<Question> questions = quiz.getQuestions();
        if (questions.isEmpty()) {
            throw new RuntimeException("No questions available for this quiz");
        }

        Question randomQuestion = questions.get(new Random().nextInt(questions.size()));
        game.setCurrentQuestion(randomQuestion);
        gameRepository.save(game);

        return randomQuestion;
    }
    public boolean isCorrectAnswer(Question question, int answer){
        return question.getCorrectOptionIndex() == answer;
    }
    private boolean checkWinner(Game game) {
        List<String> board = game.getBoard();
        String symbol = game.getCurrentPlayer().getSymbol();

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
    public Map<String, Object> playGame(Long gameId, int position, int answer) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Question currentQuestion = game.getCurrentQuestion();
        if (currentQuestion == null) {
            throw new IllegalStateException("No current question set for this game");
        }

        boolean isCorrect = isCorrectAnswer(currentQuestion, answer);

        logger.info("Question: {}, User answer: {}, Correct answer: {}, isCorrect: {}",
                currentQuestion.getQuestionText(), answer, currentQuestion.getCorrectOptionIndex(), isCorrect);

        Map<String, Object> result = new HashMap<>();
        result.put("isCorrect", isCorrect);

        if (isCorrect && game.getBoard().get(position).isEmpty()) {
            List<String> updatedBoard = new ArrayList<>(game.getBoard());
            updatedBoard.set(position, game.getCurrentPlayer().getSymbol());
            game.setBoard(updatedBoard);

            if (checkWinner(game)) {
                game.setWinner(game.getCurrentPlayer().getName());
            } else if (isBoardGameFull(game)) {
                game.setWinner("Tie");
            }
        }

        switchPlayer(game);
        game = gameRepository.save(game);
        result.put("game", game);

        return result;
    }
}
