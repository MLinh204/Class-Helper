package com.example.Class_Helper.controller;

import com.example.Class_Helper.model.Vocab;
import com.example.Class_Helper.model.VocabGameSession;
import com.example.Class_Helper.service.VocabGameService;
import com.example.Class_Helper.service.VocabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/vocab-game")
public class VocabGameController {
    @Autowired
    private VocabGameService vocabGameService;
    @GetMapping
    public ModelAndView getVocabGamePage(){
        return new ModelAndView("vocab-game");
    }
    @GetMapping("/game-session/{gameId}")
    public VocabGameSession getGameSession(@PathVariable String gameId) {
        return vocabGameService.getGameSession(gameId);
    }

    @PostMapping("/init")
    @ResponseBody
    public String initializeGame(@RequestParam(value ="studentId") Long studentId, @RequestParam(value ="vocabGroupId") Long vocabGroupId){
        String gameId = UUID.randomUUID().toString();
        vocabGameService.initializeGame(gameId, studentId, vocabGroupId);
        return gameId;
    }
    @GetMapping("/{gameId}/next-vocab")
    public Vocab getNextVocab(@PathVariable String gameId) {
        return vocabGameService.getNextVocab(gameId);
    }
    @PostMapping("/{gameId}/submit-answer")
    public void submitAnswer(
            @PathVariable String gameId,
            @RequestParam boolean isCorrect,
            @RequestParam boolean useHint) {
        vocabGameService.submitAnswer(gameId, isCorrect, useHint);
    }
    @PostMapping("/{gameId}/game-end/{studentId}")
    public VocabGameSession gameEnd(@PathVariable String gameId, @PathVariable Long studentId){
        return vocabGameService.gameEnd(gameId, studentId);
    }
}
