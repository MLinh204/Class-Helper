package com.example.Class_Helper.controller;

import com.example.Class_Helper.model.Game;
import com.example.Class_Helper.model.Question;
import com.example.Class_Helper.model.Quiz;
import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.repository.QuizRepository;
import com.example.Class_Helper.repository.StudentRepository;
import com.example.Class_Helper.service.GameService;
import com.example.Class_Helper.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/new")
    public String newGame(Model model){
        model.addAttribute("quizzes", quizRepository.findAll());
        model.addAttribute("students", studentRepository.findAll());
        return "newGame";
    }
    @PostMapping("/create")
    public String createGame(@RequestParam Long player1Id,
                             @RequestParam Long player2Id,
                             @RequestParam Long quizId,
                             Model model) {
        Student player1 = studentRepository.findById(player1Id)
                .orElseThrow(() -> new IllegalArgumentException("Player 1 not found"));
        Student player2 = studentRepository.findById(player2Id)
                .orElseThrow(() -> new IllegalArgumentException("Player 2 not found"));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        player1.setSymbol("X");
        player2.setSymbol("O");

        Game game = gameService.createGame(player1, player2, quiz.getId());
        model.addAttribute("game", game);
        model.addAttribute("questions", quiz.getQuestions());
        return "game";
    }
    @PostMapping("/{gameId}/play")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> playGame(@PathVariable Long gameId, @RequestParam Long quizId, @RequestParam int answer, @RequestParam int position) {
        Map<String, Object> result = gameService.playGame(gameId, position, answer, quizId);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{gameId}")
    public String getGame(@PathVariable Long gameId, Model model) {
        Game game = gameService.getGame(gameId);
        model.addAttribute("game", game);
        return "game";
    }
    @GetMapping("/{gameId}/question")
    @ResponseBody
    public Question getQuestion(@PathVariable Long gameId, @RequestParam Long quizId, @RequestParam int position) {
        return gameService.getRandomQuestion(quizId);
    }
}
