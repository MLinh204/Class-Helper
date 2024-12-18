package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.model.Vocab;
import com.example.Class_Helper.model.VocabGroup;
import com.example.Class_Helper.repository.StudentRepository;
import com.example.Class_Helper.repository.VocabGroupRepository;
import com.example.Class_Helper.repository.VocabRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabService {
    @Autowired
    private VocabRepository vocabRepository;
    @Autowired
    private VocabGroupRepository vocabGroupRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentRepository studentRepository;

    public List<Vocab> getVocabNotByCurrentStudent(Long vocabGroupId, Long studentId) {
        return vocabRepository.findByVocabGroupIdAndCreatorStudentIdNot(vocabGroupId, studentId);
    }
    public List<Vocab> getAllVocabsInGroup(Long vocabGroupId) {
        return vocabRepository.findByVocabGroupId(vocabGroupId);
    }

    public Vocab createVocab(String word, String wordType, Long studentId, Long vocabGroupId) {
        Student student = studentService.getStudentById(studentId);
        VocabGroup vocabGroup = vocabGroupRepository.findById(vocabGroupId).orElse(null);
        Vocab vocab = new Vocab();
        vocab.setWord(word);
        vocab.setWordType(wordType);
        vocab.setCreatorStudent(student);
        vocab.setVocabGroup(vocabGroup);
        return vocabRepository.save(vocab);
    }
    public VocabGroup createVocabGroup(String vocabGroupName) {
        VocabGroup vocabGroup = new VocabGroup();
        vocabGroup.setVocabGroupName(vocabGroupName);

        return vocabGroupRepository.save(vocabGroup);
    }
    public void updateStudentPoint(Long studentId){
        Student student = studentService.getStudentById(studentId);
        student.setPoint(student.getPoint() + 10);
        studentRepository.save(student);
    }
    public Vocab findRandomVocabToCheck(Long vocabGroupId, Long studentId){
        List<Vocab> vocabs = getVocabNotByCurrentStudent(vocabGroupId, studentId);
        if(vocabs.isEmpty()){
            return null;
        }
        int randomIndex = (int) (Math.random() * vocabs.size());
        return vocabs.get(randomIndex);
    }
    public void removeVocabFromTestList(Long vocabId, List<Vocab> currentTestList) {
        currentTestList.removeIf(vocab -> vocab.getId().equals(vocabId));
    }
}
