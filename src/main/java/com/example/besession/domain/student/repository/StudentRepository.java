package com.example.besession.domain.student.repository;

import com.example.besession.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}