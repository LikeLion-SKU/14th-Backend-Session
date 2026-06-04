package com.likelion.besession.domain.contract.entity;

import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contract_analysis")
@Getter
@NoArgsConstructor
public class ContractAnalysis extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 분석 대상 계약서 (1:1 관계)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;

    // AI가 생성한 계약서 전체 요약
    @Column(name = "ai_summary", columnDefinition = "TEXT")
    private String aiSummary;

    // AI가 분석한 계약서 주요 내용
    @Column(name = "ai_contents", columnDefinition = "TEXT")
    private String aiContents;

    // AI가 감지한 위험 이슈 목록
    @Column(name = "ai_issues", columnDefinition = "TEXT")
    private String aiIssues;

    // AI 위험도 점수 (0~100, 높을수록 위험)
    @Column(name = "ai_score")
    private Integer aiScore;

    @Builder
    public ContractAnalysis(Contract contract, String aiSummary, String aiContents,
                            String aiIssues, Integer aiScore) {
        this.contract = contract;
        this.aiSummary = aiSummary;
        this.aiContents = aiContents;
        this.aiIssues = aiIssues;
        this.aiScore = aiScore;
    }
}
