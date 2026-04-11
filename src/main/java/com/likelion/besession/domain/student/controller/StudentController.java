package com.likelion.besession.domain.student.controller;

import com.likelion.besession.domain.student.entity.Student;
import com.likelion.besession.domain.student.repository.StudentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Student API", description = "학생 관련 API")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Operation(
            summary = "전체 학생 조회",
            description = "모든 학생 정보를 조회합니다."
    )

    @GetMapping("/students")
    public List<Student> findAll(){
        return studentRepository.findAll();
    }
}
