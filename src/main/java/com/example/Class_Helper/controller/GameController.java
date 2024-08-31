package com.example.Class_Helper.controller;

import com.example.Class_Helper.model.Game;
import com.example.Class_Helper.model.Quiz;
import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.repository.QuizRepository;
import com.example.Class_Helper.service.GameService;
import com.example.Class_Helper.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private QuizRepository quizRepository;

    @GetMapping("/new")
    public String newGame(Model model){
        model.addAttribute("player1", new Student());
        model.addAttribute("player2", new Student());
        return "newGame";
    }
    @PostMapping("/create/{id}")
    public String createGame(@PathVariable Long id,@ModelAttribute Student player1, @ModelAttribute Student player2, Model model){
        player1.setSymbol("X");
        player2.setSymbol("O");
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
        Game game = gameService.createGame(player1, player2, quiz.getId());
        model.addAttribute("game",game);
        model.addAttribute("quiz", gameService.randomQuestion(quiz.getId()));
        return "game";
    }
    @PostMapping("/{gameId}/play")
    public String playGame(@PathVariable Long gameId, @RequestParam Long quizId, @RequestParam int answer, @RequestParam int position, Model model) {
        Game game = gameService.playGame(gameId, position, answer, quizId);
        model.addAttribute("game", game);
        if (game.getWinner() == null) {
            gameService.randomQuestion(quizId).ifPresent(question -> model.addAttribute("quiz", question));
        }
        return "game";
    }
}
