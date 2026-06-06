package com.likelion.besession.domain.checklist.entity;

import com.likelion.besession.domain.contract.entity.Contract;
import com.likelion.besession.domain.contract.entity.ContractStatus;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checklist")
public class Checklist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ContractStatus stage;

    @Column(nullable = false, length = 100)
    private String taskname;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer checklistOrder;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isChecked = false;

    public void toggleChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
}
