package com.example.week09likelion.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 25)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserLevel level;

    @Builder
    public User(String email, String password, String name, UserLevel level) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.level = level;
    }

    // 사용자 레벨 수정
    public void updateLevel(UserLevel level) {
        this.level = level;
    }
}