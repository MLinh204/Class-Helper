package com.example.Class_Helper.controller;

import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public String listStudents(Model model){
        model.addAttribute("students", studentService.getALlStudent());
        return "studentList";
    }
    @GetMapping("/{id}")
    public String studentDetail(@PathVariable Long id, Model model){
        model.addAttribute("student", studentService.getStudentById(id));
        return "studentDetail";
    }
    @GetMapping("/create")
    public String createStudentForm( Model model){
        model.addAttribute("student", new Student());
        return "createStudentForm";
    }
    @PostMapping("/create")
    public String createStudent(@ModelAttribute Student student){
        studentService.createStudent(student);
        return "redirect:/students";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
    @GetMapping("/modifyHeart/{id}")
    public String modifyHeartForm(@PathVariable Long id, Model model){
        model.addAttribute("studentId", id);
        return "modifyHeartForm";
    }
    @PostMapping("/modifyHeart/{id}")
    public String modifyHeart(@PathVariable Long id, @RequestParam double heartChange, @RequestParam String description){
        studentService.modifyHeart(id, heartChange, description);
        return "redirect:/students";
    }
    @GetMapping("/addPoints/{id}")
    public String addPointForm(@PathVariable Long id, Model model){
        model.addAttribute("studentId", id);
        return "addPointForm";
    }
    @PostMapping("/addPoints/{id}")
    public String addPoint(@PathVariable Long id, @RequestParam double pointToAdd, @RequestParam String description){
        studentService.addPoint(id, pointToAdd, description);
        return "redirect:/students";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student updatedStudent){
        Student existingStudent = studentService.getStudentById(id);
        existingStudent.setName(updatedStudent.getName());
        existingStudent.setHeart(updatedStudent.getHeart());
        existingStudent.setPowerType(updatedStudent.getPowerType());
        existingStudent.setPoint(updatedStudent.getPoint());
        existingStudent.setLevel(updatedStudent.getLevel());
        existingStudent.setProfilePicture(updatedStudent.getProfilePicture());

        studentService.updateStudent(updatedStudent);
        return "redirect:/students";
    }
}
