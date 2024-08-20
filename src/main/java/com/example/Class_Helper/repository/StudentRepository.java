package com.example.Class_Helper.repository;

import com.example.Class_Helper.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Modifying
    @Query("UPDATE Student s SET " +
            "s.name = CASE WHEN :#{#student.name} IS NULL THEN s.name ELSE :#{#student.name} END, " +
            "s.powerType = CASE WHEN :#{#student.powerType} IS NULL THEN s.powerType ELSE :#{#student.powerType} END, " +
            "s.profilePicture = CASE WHEN :#{#student.profilePicture} IS NULL THEN s.profilePicture ELSE :#{#student.profilePicture} END, " +
            "s.crystal = CASE WHEN :#{#student.crystal} IS NULL THEN s.crystal ELSE :#{#student.crystal} END " +
            "WHERE s.id = :#{#student.id}")
    void updateStudent(@Param("student") Student student);
}
