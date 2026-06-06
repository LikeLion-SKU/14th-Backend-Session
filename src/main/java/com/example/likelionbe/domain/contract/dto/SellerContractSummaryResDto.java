package com.example.likelionbe.domain.contract.dto;

import com.example.likelionbe.domain.contract.enums.ContractStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "SellerContractSummaryResDto: 판매자용 계약 목록 응답 DTO")
public record SellerContractSummaryResDto(
        @Schema(description = "계약 ID", example = "1")
        Long contractId,

        @Schema(description = "구매자 ID", example = "1")
        Long buyerId,

        @Schema(description = "구매자 이름", example = "홍길동")
        String buyerName,

        @Schema(description = "계약 상태", example = "REQUESTED")
        ContractStatus contractStatus,

        @Schema(description = "요청 메시지", example = "계약을 신청합니다.")
        String requestMessage,

        @Schema(description = "신청 일시", example = "2025-01-01T00:00:00")
        LocalDateTime createdAt
) {
}
