package com.likelion.besession.domain.analysis.entity;

import com.likelion.besession.domain.contract.entity.Contract;
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
@Table(name = "contract_analysis")
public class Analysis extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    @Lob
    private String contractContent;

    @Lob
    private String aiInterpretation;

    @Column(length = 2000)
    private String precautions;

    @Column(nullable = false, length = 500)
    private String imageUrl;
}
