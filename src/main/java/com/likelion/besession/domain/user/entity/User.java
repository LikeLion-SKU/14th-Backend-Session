package com.likelion.besession.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users") // user로 지정 시, DB 예약어와 충돌 가능성 존재 -> users로 지정
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // 동명이인 존재 가능 -> unique = true XXX
    private String name;

    @Column(nullable = false, unique = true) // 1개의 이메일로는, 1개의 계정만 생성 가능 -> unique = true
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Builder.Default
    private Long level = 1L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;
}
