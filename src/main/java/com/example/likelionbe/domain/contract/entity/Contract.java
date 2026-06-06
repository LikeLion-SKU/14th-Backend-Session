package com.example.likelionbe.domain.contract.entity;

import com.example.likelionbe.domain.contract.enums.ContractStatus;
import com.example.likelionbe.domain.listing.entity.Listing;
import com.example.likelionbe.domain.user.entity.User;
import com.example.likelionbe.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "contract")
public class Contract extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ContractStatus contractStatus = ContractStatus.REQUESTED;

    @Column(length = 500)
    private String requestMessage;

    private Long contractSalePrice;

    private Long contractDeposit;

    private Long contractMonthlyRent;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String contractDocumentContent;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String aiSummaryContent;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String cautionContent;

    private LocalDateTime approvedAt;

    private LocalDateTime rejectedAt;

    private LocalDateTime completedAt;

    /**
     * 계약 상태를 변경하고 변경 시점을 기록합니다.
     *
     * @param newStatus 변경할 새로운 상태
     */
    public void updateStatus(ContractStatus newStatus) {
        this.contractStatus = newStatus;
        LocalDateTime now = LocalDateTime.now();
        switch (newStatus) {
            case APPROVED -> this.approvedAt = now;
            case REJECTED -> this.rejectedAt = now;
            case COMPLETED -> this.completedAt = now;
        }
    }

    /**
     * 계약서 내용, AI 요약, 유의사항을 업데이트합니다.
     *
     * @param content   계약서 본문 내용
     * @param aiSummary AI 요약 내용
     * @param caution   유의사항 내용
     */
    public void updateDocumentContent(String content, String aiSummary, String caution) {
        this.contractDocumentContent = content;
        this.aiSummaryContent = aiSummary;
        this.cautionContent = caution;
    }
}
