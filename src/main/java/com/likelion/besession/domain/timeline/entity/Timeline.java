package com.likelion.besession.domain.timeline.entity;

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
@Table(name = "timeline")
public class Timeline extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeline_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false)
    private Integer timelineOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ContractStatus stage;

    public void update(String title, ContractStatus stage, Integer timelineOrder) {
        this.title = title;
        this.stage = stage;
        this.timelineOrder = timelineOrder;
    }
}
