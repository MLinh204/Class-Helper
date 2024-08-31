package com.example.Class_Helper.repository;

import com.example.Class_Helper.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}
