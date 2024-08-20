package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public Student createStudent(Student student){
        return studentRepository.save(student);
    }
    public Student getStudentById(Long id){
        return studentRepository.findById(id).orElse(null);
    }
    public Student updateStudent(Student student){
        return studentRepository.save(student);
    }
    public List<Student> getALlStudent (){
        return studentRepository.findAll();
    }
    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }
    public Student modifyHeart(Long id, double heartChange, String description){
        Student student = getStudentById(id);
        if(student!=null){
            double newHeartValue = student.getHeart() + heartChange;
            newHeartValue = Math.min(10, Math.max(0, newHeartValue));
            student.setHeart(newHeartValue);
            return updateStudent(student);
        }
        return null;
    }
    public Student addPoint(Long id, int pointToAdd, String description){
        Student student = getStudentById(id);
        if (student!=null){
            int totalPoint = student.getPoint()+pointToAdd;
            int newLevel = student.getLevel() + (totalPoint/2000);
            int remainingPoint = totalPoint %2000;
            student.setLevel(newLevel);
            student.setPoint(remainingPoint);
            return updateStudent(student);
        }
        return null;
    }

    public Student getRandomStudent() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(students.size());
        return students.get(randomIndex);
    }

    public Student modifyCrystal(Long id, int crystalChange, String description){
        Student student = getStudentById(id);
        if (student!=null){
            int newCrystal = student.getCrystal() + crystalChange;
            newCrystal = Math.min(10, Math.max(0, newCrystal));
            student.setCrystal(newCrystal);
            return updateStudent(student);
        }
        return null;
    }
}
