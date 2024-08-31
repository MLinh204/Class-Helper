package com.example.Class_Helper.repository;

import com.example.Class_Helper.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
