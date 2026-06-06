package com.likelion.besession.domain.contract.entity;

import com.likelion.besession.global.common.BaseLocalDateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "checklist")
public class Checklist extends BaseLocalDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(length = 30)
    private String title;

    @Column(length = 255)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ContractPhase phase;

    @Builder.Default
    private boolean status = false;

    public void updateStatus(boolean status) {
        this.status = status;
    }
}
