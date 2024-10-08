package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Question;
import com.example.Class_Helper.model.Quiz;
import com.example.Class_Helper.repository.QuestionRepository;
import com.example.Class_Helper.repository.QuizRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    // Quiz related methods
    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    public Quiz findQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(Quiz updatedQuiz) {
        logger.info("Quiz: {}", updatedQuiz.getName());
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
                    logger.info("Question: {}, Options: {}, Correct Option: {}",
                            question.getQuestionText(), question.getOptions(), question.getCorrectOptionIndex());
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