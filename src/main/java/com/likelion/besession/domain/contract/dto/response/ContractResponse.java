package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.Contract;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "ContractResponse: 계약서 응답 DTO")
public class ContractResponse {

    @Schema(description = "계약서 ID", example = "1")
    private Long id;

    @Schema(description = "계약 상대방 이름", example = "김임대")
    private String contractPartnerName;

    @Schema(description = "계약서 파일 URL")
    private String contractDoc;

    @Schema(description = "계약 시작일", example = "2024-01-01")
    private LocalDate startDate;

    @Schema(description = "계약 종료일", example = "2025-12-31")
    private LocalDate endDate;

    @Schema(description = "AI 분석 완료 여부", example = "false")
    private boolean isAnalyzed;

    @Schema(description = "생성일시")
    private LocalDateTime createdAt;

    public static ContractResponse from(Contract contract) {
        return ContractResponse.builder()
                .id(contract.getId())
                .contractPartnerName(contract.getContractPartnerName())
                .contractDoc(contract.getContractDoc())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .isAnalyzed(contract.isAnalyzed())
                .createdAt(contract.getCreatedDate())
                .build();
    }
}
