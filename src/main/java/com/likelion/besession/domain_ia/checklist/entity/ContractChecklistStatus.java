package com.likelion.besession.domain_ia.checklist.entity;

import com.likelion.besession.domain_ia.contract.entity.Contract;
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
public class ContractChecklistStatus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Process process;

    @Builder.Default
    private boolean isChecked = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    private Checklist checklist;
}
