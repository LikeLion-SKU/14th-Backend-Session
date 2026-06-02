package com.likelion.besession.domain.contract.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "ContractListResponse: 계약 전체 목록 응답 DTO")
public class ContractListResponse {

    @Schema(description = "계약 ID", example = "1")
    private Long contractId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "생성 시각", example = "2026-05-30T10:00:00")
    private LocalDateTime createdAt;
}
