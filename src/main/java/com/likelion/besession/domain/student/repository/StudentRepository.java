package com.likelion.besession.domain.student.repository;

import com.likelion.besession.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
