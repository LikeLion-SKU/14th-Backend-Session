package com.likelion.besession.domain.student.repository;

import com.likelion.besession.domain.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
// 상속 받는 JpaRepository -> <엔티티명, 아이디 자료형>
public interface StudentRepository extends JpaRepository<Student, Long> {
}
