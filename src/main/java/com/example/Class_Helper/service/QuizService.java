package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Question;
import com.example.Class_Helper.model.Quiz;
import com.example.Class_Helper.repository.QuestionRepository;
import com.example.Class_Helper.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // Quiz related methods
    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> findQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Optional<Quiz> updateQuiz(Long id, Quiz updatedQuiz) {
        return quizRepository.findById(id)
                .map(quiz -> {
                    quiz.setName(updatedQuiz.getName());
                    // Update other fields as necessary
                    return quizRepository.save(quiz);
                });
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
                    // Update other fields as necessary
                    return questionRepository.save(question);
                });
    }

    public boolean deleteQuestionByQuizId(Long quizId, Long questionId) {
        return quizRepository.findById(quizId)
                .map(quiz -> {
                    boolean removed = quiz.getQuestions().removeIf(q -> q.getId().equals(questionId));
                    if (removed) {
                        quizRepository.save(quiz);
                        questionRepository.deleteById(questionId);
                    }
                    return removed;
                })
                .orElse(false);
    }
}