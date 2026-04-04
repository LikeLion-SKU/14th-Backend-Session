package com.example.likelionbe.domain.student.repository;

import com.example.likelionbe.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
