package com.example.likelionbe.domain.contract.dto;

import com.example.likelionbe.domain.contract.enums.ContractStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "ContractStatusResDto: 계약 상태 응답 DTO")
public record ContractStatusResDto(
        @Schema(description = "계약 ID", example = "1")
        Long contractId,

        @Schema(description = "계약 상태", example = "REQUESTED")
        ContractStatus contractStatus,

        @Schema(description = "변경 일시", example = "2025-01-01T00:00:00")
        LocalDateTime updatedAt
) {
}
