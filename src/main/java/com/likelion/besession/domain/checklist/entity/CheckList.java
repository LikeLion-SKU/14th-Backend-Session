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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "check_lists")
public class CheckList extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(nullable = false, length = 100)
    private String checkListTitle;

    @Column(nullable = false, length = 500)
    private String checkListContent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private CheckListStatus checkListStatus = CheckListStatus.INCOMPLETE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContractStatus phase;

    public void complete() {
        this.checkListStatus = CheckListStatus.COMPLETE;
    }

    public void incomplete() {
        this.checkListStatus = CheckListStatus.INCOMPLETE;
    }
}
