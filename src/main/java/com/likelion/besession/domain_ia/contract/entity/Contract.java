package com.likelion.besession.domain_ia.contract.entity;

import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain_ia.contract.entity.Process;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract extends BaseTimeEntity {

    // 계약서 id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 계약서 이미지 URL
    private String contractImageURL;

    // 부동산 주소
    private String address;

    // 계약 진행상태
    @Builder.Default
    private Process currentProcess = Process.BEFORE;

    // 계약 종료 여부
    @Builder.Default
    private boolean isDone = false;

    // 유저 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
