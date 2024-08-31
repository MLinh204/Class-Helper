package com.example.Class_Helper.repository;

import com.example.Class_Helper.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
