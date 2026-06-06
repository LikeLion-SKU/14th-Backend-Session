package com.example.likelionbe.domain.contract.dto;

import com.example.likelionbe.domain.contract.enums.ContractStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "ContractApplyResDto: 계약 신청 응답 DTO")
public record ContractApplyResDto(
        @Schema(description = "계약 ID", example = "1")
        Long contractId,

        @Schema(description = "매물 ID", example = "1")
        Long listingId,

        @Schema(description = "구매자 ID", example = "1")
        Long buyerId,

        @Schema(description = "계약 상태", example = "REQUESTED")
        ContractStatus contractStatus,

        @Schema(description = "생성된 체크리스트 항목 수", example = "7")
        Integer checklistsCreated,

        @Schema(description = "신청 일시", example = "2025-01-01T00:00:00")
        LocalDateTime createdAt
) {
}
