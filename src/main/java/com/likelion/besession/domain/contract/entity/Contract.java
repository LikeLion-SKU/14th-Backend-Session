package com.likelion.besession.domain.contract.entity;

import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "contracts")
public class Contract extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ContractStatus contractStatus = ContractStatus.BEFORE;

    public void progressStatus() {
        switch (this.contractStatus) {
            case BEFORE -> this.contractStatus = ContractStatus.DURING;
            case DURING -> this.contractStatus = ContractStatus.AFTER;
            case AFTER -> throw new IllegalStateException("이미 계약 후 단계입니다.");
        }
    }
}
