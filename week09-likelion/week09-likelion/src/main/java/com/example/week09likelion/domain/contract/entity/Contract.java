package com.example.week09likelion.domain.contract.entity;

import com.example.week09likelion.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "contract",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_contract_user", columnNames = "user_id")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 한 명의 사용자는 계약 하나만 가질 수 있음
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContractStatus status;

    @Column(nullable = false)
    private Integer progress;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Contract(User user, String title, String address, ContractStatus status, Integer progress) {
        this.user = user;
        this.title = title;
        this.address = address;
        this.status = status;
        this.progress = progress;
        this.createdAt = LocalDateTime.now();
    }

    // 계약 정보 수정
    public void updateContract(String title, String address, ContractStatus status, Integer progress) {
        this.title = title;
        this.address = address;
        this.status = status;
        this.progress = progress;
    }
}