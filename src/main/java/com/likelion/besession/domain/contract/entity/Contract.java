package com.likelion.besession.domain.contract.entity;

import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자는 계약 하나만 가짐
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Lob
    @Column(nullable = false)
    private String contractSource;

    @Column(nullable = false)
    private String contractStep;

    @Column(nullable = false)
    private Integer progressRate;

    public void update(
            String name,
            String address,
            String contractSource,
            String contractStep,
            Integer progressRate
    ) {
        this.name = name;
        this.address = address;
        this.contractSource = contractSource;
        this.contractStep = contractStep;
        this.progressRate = progressRate;
    }
}