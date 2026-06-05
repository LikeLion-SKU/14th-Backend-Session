package com.likjelion.besession.week09_domain.analysis.entity;

import com.likjelion.besession.global.common.BaseTimeEntity;
import com.likjelion.besession.week09_domain.contract.entity.Contract;
import com.likjelion.besession.week09_domain.report.entity.Report;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "analysis")
public class Analysis extends BaseTimeEntity {

    @Id @Column(name = "analysis_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contractContent;

    @Column(nullable = false)
    private String aiComment;

    @Column(nullable = false)
    private String warning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;


    private LocalDateTime deleted_at;


}
