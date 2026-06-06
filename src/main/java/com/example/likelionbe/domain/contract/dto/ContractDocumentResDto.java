package com.example.likelionbe.domain.contract.dto;

import com.example.likelionbe.domain.contract.enums.ContractStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title = "ContractDocumentResDto: 계약서 문서 응답 DTO")
public record ContractDocumentResDto(
        @Schema(description = "계약 ID", example = "1")
        Long contractId,

        @Schema(description = "계약서 본문 내용")
        String contractDocumentContent,

        @Schema(description = "AI 요약 내용")
        String aiSummaryContent,

        @Schema(description = "유의사항 내용")
        String cautionContent,

        @Schema(description = "계약 상태", example = "APPROVED")
        ContractStatus contractStatus
) {
}
