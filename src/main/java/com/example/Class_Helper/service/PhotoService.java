package com.example.Class_Helper.service;

import com.example.Class_Helper.model.Student;
import com.example.Class_Helper.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class PhotoService {
    private final String UPLOAD_DIR = "D:/uploads";
    @Autowired
    private StudentRepository studentRepository;

    public void saveStudentPhoto(Student student, MultipartFile photo){
        if (!photo.isEmpty()){
            try {
                byte[] bytes = photo.getBytes();
                student.setPhoto(bytes);
                studentRepository.save(student);

                String fileName = student.getName() + "_" + System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + "/" + fileName);
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] getEmployeePhoto(Long employeeId) {
        Student student = studentRepository.findById(employeeId).orElse(null);
        return student != null ? student.getPhoto() : null;
    }
}
