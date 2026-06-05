package com.likjelion.besession.week09_domain.checklist.entity;

import com.likjelion.besession.global.common.BaseTimeEntity;
import com.likjelion.besession.week09_domain.contract.entity.Contract;
import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
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
@Table(name = "contract_checklist")
public class ContractCheckList extends BaseTimeEntity {

    @Id @Column(name = "contract_checklist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false)
    private boolean checked = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus contractStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private CheckList checkList;
}
