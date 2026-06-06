package com.likelion.besession.domain.checklist.entity;

import com.likelion.besession.domain.contract.entity.Contract;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Checklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 하나의 계약은 여러 개의 체크리스트를 가질 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contractStep;

    @Column(nullable = false)
    private Boolean checked;

    public void updateChecked(Boolean checked) {
        this.checked = checked;
    }
}