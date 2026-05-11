package com.example.likelionkang.domain.user.entity;

import com.example.likelionkang.domain.global.BaseTimeEntitiy;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
// JPA 엔티티는 기본 생성자가 필수입니다. 외부 생성을 막기 위해 PROTECTED 권한 권장.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // Builder 사용을 위해 모든 필드 생성자 추가
public class User extends BaseTimeEntitiy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    // @JoinColumn은 관계 매핑(FK)용이므로 삭제해야 합니다.
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.USER;
}