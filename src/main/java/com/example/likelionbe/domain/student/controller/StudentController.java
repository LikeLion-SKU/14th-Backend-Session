package com.example.likelionbe.domain.student.controller;

import com.example.likelionbe.domain.student.entity.Student;
import com.example.likelionbe.domain.student.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public List<Student> findAll(){
        return studentRepository.findAll();
    }
}