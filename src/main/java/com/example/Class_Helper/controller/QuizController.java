package com.example.Class_Helper.controller;

import com.example.Class_Helper.model.Question;
import com.example.Class_Helper.model.Quiz;
import com.example.Class_Helper.service.QuizService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public String getAllQuiz(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuiz());
        model.addAttribute("quiz", new Quiz());
        return "quiz";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        Quiz quiz = quizService.findQuizById(id);
        List<Question> questions = quizService.getAllQuestionsByQuizId(id).orElse(new ArrayList<>());
        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);
        return "quizDetail";
    }

    @PostMapping("/create")
    public String createQuiz(@ModelAttribute Quiz quiz) {
        quizService.createQuiz(quiz);
        return "redirect:/quiz";
    }

    @PostMapping("/update/{id}")
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz updatedQuiz) {
        Quiz existingQuiz = quizService.findQuizById(id);
        if (existingQuiz != null) {
            existingQuiz.setName(updatedQuiz.getName());
            quizService.updateQuiz(existingQuiz);
        }
        return "redirect:/quiz";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quiz";
    }

    @PostMapping("/{quizId}/create-question")
    public String createQuestion(@ModelAttribute Question question, @PathVariable Long quizId) {
        quizService.createQuestionByQuizId(question, quizId);
        return "redirect:/quiz/" + quizId;
    }

    @PostMapping("/{quizId}/update-question/{questionId}")
    public String updateQuestion(@PathVariable Long quizId, @PathVariable Long questionId, @ModelAttribute Question updatedQuestion) {
        quizService.updateQuestionByQuizId(quizId, questionId, updatedQuestion);
        return "redirect:/quiz/" + quizId;
    }

    @GetMapping("/{quizId}/delete-question/{questionId}")
    public String deleteQuestion(@PathVariable Long quizId, @PathVariable Long questionId) {
        quizService.deleteQuestionByQuizId(quizId, questionId);
        return "redirect:/quiz/" + quizId;
    }
}