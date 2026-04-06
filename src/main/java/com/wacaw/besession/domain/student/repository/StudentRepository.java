package com.wacaw.besession.domain.student.repository;

import com.wacaw.besession.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
