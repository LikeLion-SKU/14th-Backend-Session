package com.example.likelionkang.domain.student.repository;

import com.example.likelionkang.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}