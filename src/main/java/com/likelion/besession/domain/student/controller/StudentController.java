package com.likelion.besession.domain.student.controller;

import com.likelion.besession.domain.student.entity.Student;
import com.likelion.besession.domain.student.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    private final StudentRepository studentRepository; // 리포지토리 객체 생성

    // 생성자 인젝션
    public StudentController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public List<Student> findAll(){
        return studentRepository.findAll();
    }
}
