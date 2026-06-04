package com.likelion.besession.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 이름
    @Column(nullable = false)
    private String name;

    // 로그인에 사용하는 이메일 (유니크)
    @Column(nullable = false, unique = true)
    private String email;

    // 암호화된 비밀번호 (JSON 직렬화 제외)
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    // 프로필 이미지 URL (nullable)
    @Column(name = "profile_img")
    private String profileImg;

    // 사용자 활동 레벨 (기본값 1)
    @Column(name = "user_level", nullable = false)
    private int userLevel = 1;

    // 신뢰도 점수 (0.0 ~ 1.0, 기본값 0.0)
    @Column(nullable = false)
    private float trust = 0.0f;

    // 권한 (USER / ADMIN)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    // 회원가입 시 사용하는 생성자
    @Builder
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role != null ? role : Role.USER;
        this.userLevel = 1;
        this.trust = 0.0f;
    }

    // 프로필 수정 메서드 (null이면 기존 값 유지)
    public void updateProfile(String name, String profileImg) {
        if (name != null && !name.isBlank()) this.name = name;
        if (profileImg != null) this.profileImg = profileImg;
    }
}
