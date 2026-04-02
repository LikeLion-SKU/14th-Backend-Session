package com.example.besession.domain.student.entity;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private String department;

    @Column(name = "student_num")
    private String studentNum;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getDepartment() {
        return department;
    }

    public String getStudentNum() {
        return studentNum;
    }
}