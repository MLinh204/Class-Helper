package com.example.Class_Helper.controller;

import com.example.Class_Helper.MultipartFileByteArrayEditor;
import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.service.GameService;
import com.example.Class_Helper.service.PhotoService;
import com.example.Class_Helper.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private PhotoService photoService;

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentService.getALlStudent();
        List<String> base64Photos = new ArrayList<>();
        for (Student student : students) {
            base64Photos.add(student.getPhoto()!= null? Base64.getEncoder().encodeToString(student.getPhoto()) : null);
        }
        model.addAttribute("base64Photos", base64Photos);
        model.addAttribute("students", students);
        return "studentList";
    }

    @GetMapping("/api/all")
    public ResponseEntity<Map<String, Object>> getStudents() {
        List<Student> students = studentService.getALlStudent();
        List<String> base64Photos = new ArrayList<>();
        for (Student student : students) {
            base64Photos.add(student.getPhoto() != null ? Base64.getEncoder().encodeToString(student.getPhoto()) : null);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("students", students);
        response.put("base64Photos", base64Photos);

        return ResponseEntity.ok(response); // Return 200 OK with the JSON body
    }

    @GetMapping("/all")
    public String getAllStudents(Model model) {
        List<Student> students = studentService.getALlStudent();
        model.addAttribute("students", students);
        return "studentList";
    }

    @GetMapping("/details/{id}")
    public String getStudentDetails(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        String base64Photo =student.getPhoto() != null ?
                Base64.getEncoder().encodeToString(student.getPhoto()) : null;
        model.addAttribute("student", student);
        model.addAttribute("base64Photo", base64Photo);
        return "studentDetail";
    }
    @GetMapping("/api/details/{id}")
    public ResponseEntity<Map<String, Object>> getStudentDetails(@PathVariable Long id){
        try {
            Student student = studentService.getStudentById(id);
            if (student == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Student not found"));
            }
            String base64Photo = student.getPhoto() != null ?
                    Base64.getEncoder().encodeToString(student.getPhoto()) : null;
            Map<String, Object> response = new HashMap<>();
            response.put("student", student);
            response.put("base64Photo", base64Photo);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/create")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "createStudentForm";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new MultipartFileByteArrayEditor());
    }
    @PostMapping("/create")
    public String createStudent(@ModelAttribute Student student,
                                Model model,
                                @RequestParam(name = "photo", required = false) MultipartFile photo)
             {
        try {
            studentService.createStudent(student);
            if (photo != null && !photo.isEmpty()) {
                photoService.saveStudentPhoto(student, photo);
            }
            return "redirect:/students";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("student", student);
            return "studentDetail";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @PostMapping("/modifyHeart/{id}")
    public String modifyHeart(@PathVariable Long id, @RequestParam double heartChange, @RequestParam String description) {
        studentService.modifyHeart(id, heartChange, description);
        return "redirect:/students/details/" + id;
    }

    @PostMapping("/addPoints/{id}")
    public String addPoint(@PathVariable Long id, @RequestParam int pointToAdd, @RequestParam String description) {
        studentService.addPoint(id, pointToAdd, description);
        return "redirect:/students/details/" + id;
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student updatedStudent, BindingResult result, @RequestParam(value = "photo") MultipartFile photo, Model model) {
        if(result.hasErrors()){
            return "studentList";
        }
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent != null) {
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setPowerType(updatedStudent.getPowerType());

            studentService.updateStudent(existingStudent);
            photoService.saveStudentPhoto(existingStudent, photo);
        }
        model.addAttribute("student", existingStudent);
        model.addAttribute("base64Photo", photo!= null? Base64.getEncoder().encodeToString(existingStudent.getPhoto()) : null);
        return "studentDetail";
    }


    @GetMapping("/random")
    public String selectStudentForm(Model model) {
        List<Student> students = studentService.getALlStudent();
        List<String> base64Photos = students.stream()
                .map(student -> student.getPhoto() != null ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(student.getPhoto()) : null)
                .collect(Collectors.toList());

        model.addAttribute("base64Photos", base64Photos);
        model.addAttribute("students", students);
        return "selectRandomForm";
    }

    @PostMapping("/random")
    @ResponseBody
    public Student selectStudent() {
        return studentService.getRandomStudent();
    }

    @PostMapping("/modifyCrystal/{id}")
    public String modifyCrystal(@PathVariable Long id, @RequestParam int newCrystal, @RequestParam String description) {
        studentService.modifyCrystal(id, newCrystal, description);
        return "redirect:/students/details/" + id;
    }
}
