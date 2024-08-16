package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
            student.setHeart(student.getHeart() + heartChange);
            return updateStudent(student);
        }
        return null;
    }
    public Student addPoint(Long id, double pointToAdd, String description){
        Student student = getStudentById(id);
        if (student!=null){
            double totalPoint = student.getPoint()+pointToAdd;
            int newLevel = student.getLevel() + (int)(totalPoint/2000);
            double remainingPoint = totalPoint %2000;
            student.setLevel(newLevel);
            student.setPoint(remainingPoint);
            return updateStudent(student);
        }
        return null;
    }
}
