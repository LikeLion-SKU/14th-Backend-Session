package com.likelion.besession.domain.contract.entity;

import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "contract")
@Getter
@NoArgsConstructor
public class Contract extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 계약서를 등록한 사용자 (계약자 본인)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 계약 상대방 이름 (임대인 or 임차인)
    @Column(name = "contract_partner_name", nullable = false)
    private String contractPartnerName;

    // 계약서 파일 URL 또는 저장 경로
    @Column(name = "contract_doc")
    private String contractDoc;

    // 계약 시작일
    @Column(name = "start_date")
    private LocalDate startDate;

    // 계약 종료일 (nullable: 만료 날짜가 없는 계약도 있음)
    @Column(name = "end_date")
    private LocalDate endDate;

    // AI 분석 완료 여부 (분석 전 기본값 false)
    @Column(name = "is_analyzed", nullable = false)
    private boolean isAnalyzed = false;

    @Builder
    public Contract(User user, String contractPartnerName, String contractDoc,
                    LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.contractPartnerName = contractPartnerName;
        this.contractDoc = contractDoc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAnalyzed = false;
    }

    // AI 분석 완료 시 호출해서 isAnalyzed를 true로 변경
    public void markAnalyzed() {
        this.isAnalyzed = true;
    }
}
