package com.example.Class_Helper.repository;

import com.example.Class_Helper.model.Game;
import com.example.Class_Helper.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByQuizCategory(Quiz quiz);

    @Modifying
    @Query("UPDATE Game g SET g.currentQuestion = NULL WHERE g.currentQuestion.id = :questionId")
    void updateCurrentQuestionToNull(@Param("questionId") Long questionId);
}
