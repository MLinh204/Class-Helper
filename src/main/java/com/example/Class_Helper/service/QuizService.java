package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Game;
import com.example.Class_Helper.model.Question;
import com.example.Class_Helper.model.Quiz;
import com.example.Class_Helper.repository.GameRepository;
import com.example.Class_Helper.repository.QuestionRepository;
import com.example.Class_Helper.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final GameRepository gameRepository;
    @Autowired
    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository, GameRepository gameRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.gameRepository = gameRepository;
    }


    // Quiz related methods
    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    public Quiz findQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteQuiz(Long id) {
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        // First, handle Game references
        List<Game> games = gameRepository.findByQuizCategory(quiz);
        for (Game game : games) {
            game.setQuizCategory(null);
            game.setCurrentQuestion(null);
            gameRepository.save(game);
        }

        // Now, delete questions
        for (Question question : quiz.getQuestions()) {
            deleteQuestion(question.getId());
        }

        // Finally, delete the quiz
        quizRepository.delete(quiz);
    }
    @Transactional
    private void deleteQuestion(Long questionId) {
        gameRepository.updateCurrentQuestionToNull(questionId);
        questionRepository.deleteById(questionId);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Quiz updatedQuiz) {
        return quizRepository.save(updatedQuiz);
    }

    // Question related methods
    public Optional<List<Question>> getAllQuestionsByQuizId(Long quizId) {
        return quizRepository.findById(quizId)
                .map(Quiz::getQuestions);
    }

    public Optional<Question> findQuestionByQuizId(Long quizId, Long questionId) {
        return quizRepository.findById(quizId)
                .flatMap(quiz -> quiz.getQuestions().stream()
                        .filter(q -> q.getId().equals(questionId))
                        .findFirst());
    }

    public Optional<Question> createQuestionByQuizId(Question question, Long quizId) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    System.out.println("Creating Question: " + question.getQuestionText());
                    System.out.println("Options: " + question.getOptions());
                    System.out.println("Correct Option Index: " + question.getCorrectOptionIndex());
                    question.setQuiz(quiz);
                    Question savedQuestion = questionRepository.save(question);
                    quiz.getQuestions().add(savedQuestion);
                    quizRepository.save(quiz);
                    return savedQuestion;
                });
    }

    public Optional<Question> updateQuestionByQuizId(Long quizId, Long questionId, Question updatedQuestion) {
        return findQuestionByQuizId(quizId, questionId)
                .map(question -> {
                    question.setQuestionText(updatedQuestion.getQuestionText());
                    question.setOptions(updatedQuestion.getOptions());
                    question.setCorrectOptionIndex(updatedQuestion.getCorrectOptionIndex());
                    return questionRepository.save(question);
                });
    }
    @Transactional
    public boolean deleteQuestionByQuizId(Long quizId, Long questionId) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    boolean removed = quiz.getQuestions().removeIf(q -> q.getId().equals(questionId));
                    if (removed) {
                        quizRepository.save(quiz);
                        deleteQuestion(questionId);
                    }
                    return removed;
                })
                .orElse(false);
    }
}