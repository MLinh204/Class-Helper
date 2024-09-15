package com.example.Class_Helper.controller;

import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Value("${file.upload-dir}")
    private String uploadDir;

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
    @GetMapping("/all")
    @ResponseBody
    public List<Student> getAllStudents() {
        return studentService.getALlStudent();
    }

    @GetMapping("/{id}/details")
    @ResponseBody
    public Student getStudentDetails(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/create")
    public String createStudentForm( Model model){
        model.addAttribute("student", new Student());
        return "createStudentForm";
    }
    @PostMapping("/create")
    public String createStudent(@ModelAttribute Student student,
                                @RequestParam("profilePicture") MultipartFile file)
            throws IOException {
        if (!file.isEmpty()) {
            // Generate a unique file name
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            // Create the full path
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(newFileName);

            // Copy the file, replacing existing files
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not store file " + newFileName + ". Please try again!", e);
            }

            student.setProfilePictureName(newFileName);
        }

        studentService.createStudent(student);
        return "redirect:/students";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @PostMapping("/modifyHeart/{id}")
    public String modifyHeart(@PathVariable Long id, @RequestParam double heartChange, @RequestParam String description){
        studentService.modifyHeart(id, heartChange, description);
        return "redirect:/students/" + id;
    }
    @PostMapping("/addPoints/{id}")
    public String addPoint(@PathVariable Long id, @RequestParam int pointToAdd, @RequestParam String description){
        studentService.addPoint(id, pointToAdd, description);
        return "redirect:/students/" + id;
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,
                                @ModelAttribute Student updatedStudent,
                                @RequestParam("profilePicture") MultipartFile file) throws IOException {
        Student existingStudent = studentService.getStudentById(id);

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setPowerType(updatedStudent.getPowerType());
        existingStudent.setLevel(updatedStudent.getLevel());

        if (!file.isEmpty()) {
            // Generate a unique file name
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + fileExtension;

            // Create the full path
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(newFileName);

            // Copy the file, replacing existing files
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new IOException("Could not store file " + newFileName + ". Please try again!", e);
            }

            // Delete the old profile picture if it exists
            if (existingStudent.getProfilePictureName() != null) {
                Path oldFilePath = uploadPath.resolve(existingStudent.getProfilePictureName());
                Files.deleteIfExists(oldFilePath);
            }

            existingStudent.setProfilePictureName(newFileName);
        }

        studentService.updateStudent(existingStudent);
        return "redirect:/students/" + id;
    }
    @GetMapping("/random")
    public String selectStudentForm(Model model){
        List<Student> students = studentService.getALlStudent();
        model.addAttribute("students", students);
        return "selectRandomForm";
    }
    @PostMapping("/random")
    @ResponseBody
    public Student selectStudent() {
        return studentService.getRandomStudent();
    }
    @PostMapping("/modifyCrystal/{id}")
    public String modifyCrystal (@PathVariable Long id, @RequestParam int newCrystal, @RequestParam String description){
        studentService.modifyCrystal(id, newCrystal, description);
        return "redirect:/students/" + id;
    }
}
