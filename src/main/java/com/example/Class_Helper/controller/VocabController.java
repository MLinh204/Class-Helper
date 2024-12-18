package com.example.Class_Helper.controller;

import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.model.Vocab;
import com.example.Class_Helper.model.VocabGroup;
import com.example.Class_Helper.repository.VocabGroupRepository;
import com.example.Class_Helper.repository.VocabRepository;
import com.example.Class_Helper.service.StudentService;
import com.example.Class_Helper.service.VocabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VocabController {
    @Autowired
    private VocabService vocabService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private VocabGroupRepository vocabGroupRepository;
    @Autowired
    private VocabRepository vocabRepository;
    @GetMapping("/vocab-checking")
    public String getVocabCheckingPage(Model model){
        List<Student> students = studentService.getALlStudent();
        model.addAttribute("students", students);
        return "vocabChecking";
    }
    @GetMapping("/vocab-group/api/all")
    public ResponseEntity<List<VocabGroup>> getAllVocabGroups() {
        List<VocabGroup> groups = vocabGroupRepository.findAll();
        return ResponseEntity.ok(groups);
    }
    @GetMapping("/vocab-checking/{studentId}")
    public String getStudentVocabCheckingPage(@PathVariable Long studentId, Model model){
        List<VocabGroup> groups = vocabGroupRepository.findAll();
        Student student = studentService.getStudentById(studentId);
        model.addAttribute("student", student);
        model.addAttribute("vocabGroups", groups);
        return "studentVocabChecking";
    }
    @GetMapping("/{studentId}/{vocabGroupId}")
    public String getCheckVocab(@PathVariable Long studentId, @PathVariable Long vocabGroupId, Model model) {
        Student student = studentService.getStudentById(studentId);
        VocabGroup vocabGroup = vocabGroupRepository.findById(vocabGroupId).orElse(null);
        List<Vocab> vocabs = vocabService.getVocabNotByCurrentStudent(vocabGroupId, studentId);

        model.addAttribute("student", student);
        model.addAttribute("vocabGroup", vocabGroup);
        model.addAttribute("vocabs", vocabs);

        if (!vocabs.isEmpty()) {
            Vocab randomVocab = vocabService.findRandomVocabToCheck(vocabGroupId, studentId);
            model.addAttribute("randomVocab", randomVocab);
        } else {
            model.addAttribute("message", "No vocab left to test!");
        }

        return "vocabDaily"; // Render the "vocabDaily" view.
    }
    @PostMapping("/{studentId}/{vocabGroupId}/check")
    public String checkVocab(@PathVariable Long studentId, @PathVariable Long vocabGroupId,
                             @RequestParam boolean isCorrect, @RequestParam Long vocabId, Model model) {
        Student student = studentService.getStudentById(studentId);
        List<Vocab> currentTestList = vocabService.getVocabNotByCurrentStudent(vocabGroupId, studentId);

        if (isCorrect) {
            vocabService.updateStudentPoint(studentId);
            vocabService.removeVocabFromTestList(vocabId, currentTestList);
            model.addAttribute("message", "Correct! You've earned 10 points.");
        } else {
            model.addAttribute("message", "Incorrect. Better luck next time!");
        }

        // Recalculate a random vocab for the next test round if the list isn't empty
        if (!currentTestList.isEmpty()) {
            Vocab randomVocab = currentTestList.get((int) (Math.random() * currentTestList.size()));
            model.addAttribute("randomVocab", randomVocab);
        } else {
            model.addAttribute("message", "No vocab left to test!");
        }

        return "vocabDaily";
    }

    @GetMapping("/vocab-create")
        public String getVocabCreatingPage(Model model){
            List<Student> students = studentService.getALlStudent();
            model.addAttribute("students", students);
            return "vocab-create";
        }
        @GetMapping("/vocab-create/{studentId}")
        public String getStudentVocabCreatingPage(@PathVariable Long studentId, Model model){
            List<VocabGroup> groups = vocabGroupRepository.findAll();
            Student student = studentService.getStudentById(studentId);
            model.addAttribute("student", student);
            model.addAttribute("vocabGroups", groups);
            return "studentVocabCreating";
        }
        @PostMapping("/vocab-create/{studentId}")
        public String createVocabGroup(@RequestParam("vocabGroupName") String vocabGroupName, Model model, @PathVariable Long studentId) {
            if (vocabGroupName == null || vocabGroupName.trim().isEmpty()) {
                model.addAttribute("error", "Vocabulary group name cannot be empty.");
                return "studentVocabCreating"; // Return the same page with an error message.
            }
            VocabGroup vocabGroup = vocabService.createVocabGroup(vocabGroupName);
            if (vocabGroup == null) {
                model.addAttribute("error", "Failed to create vocabulary group.");
                return "studentVocabCreating"; // Return the same page with an error message.
            }

            Student student = studentService.getStudentById(studentId);
            List<VocabGroup> groups = vocabGroupRepository.findAll();
        model.addAttribute("student", student);
        model.addAttribute("success", "Vocabulary group created successfully!");
        model.addAttribute("vocabGroups", groups);
        return "studentVocabCreating"; // Render the "studentVocabCreating" view.
    }
    @GetMapping("/vocab-create/{studentId}/{vocabGroupId}")
    public String vocabList(@PathVariable Long studentId, @PathVariable Long vocabGroupId, Model model){
        Student student = studentService.getStudentById(studentId);
        VocabGroup vocabGroup = vocabGroupRepository.findById(vocabGroupId).orElse(null);
        List<Vocab> vocabs = vocabGroup.getVocabs();
        model.addAttribute("student", student);
        model.addAttribute("vocabGroup", vocabGroup);
        model.addAttribute("vocabs", vocabs);
        return "vocabList";
    }
    @PostMapping("/vocab-create/{studentId}/{vocabGroupId}")
    public String createVocab(@RequestParam("word") String word, @RequestParam("wordType") String wordType , Model model, @PathVariable Long studentId, @PathVariable Long vocabGroupId) {
        Student student = studentService.getStudentById(studentId);
        VocabGroup vocabGroup = vocabGroupRepository.findById(vocabGroupId).orElse(null);
        Vocab vocabCheckExists = vocabRepository.findByWordAndWordType(word, wordType);

        model.addAttribute("student", student);
        model.addAttribute("vocabGroup", vocabGroup);
        if (word == null || word.trim().isEmpty() || wordType == null || wordType.trim().isEmpty()) {
            model.addAttribute("error", "Word and word type cannot be empty.");
            return "vocabList"; // Return the same page with an error message.
        }
        if (vocabCheckExists!=null) {
            if (word.equalsIgnoreCase(vocabCheckExists.getWord()) && wordType.equalsIgnoreCase(vocabCheckExists.getWordType())){
                model.addAttribute("error", "Vocabulary already exists.");
                return "vocabList"; // Return the same page with an error message.
            }
        }
        Vocab vocab = vocabService.createVocab(word, wordType, studentId,vocabGroupId);
        if (vocab == null) {
            model.addAttribute("error", "Failed to create vocabulary.");
            return "vocabList"; // Return the same page with an error message.
        }
        model.addAttribute("success", "Vocabulary created successfully!");
        List<Vocab> vocabs = vocabGroup.getVocabs();
        model.addAttribute("vocabs", vocabs);
        return "vocabList"; // Render the "vocabList" view.
    }

}
