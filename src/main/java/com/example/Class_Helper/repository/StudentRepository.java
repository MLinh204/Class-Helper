package com.example.Class_Helper.repository;

import com.example.Class_Helper.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
