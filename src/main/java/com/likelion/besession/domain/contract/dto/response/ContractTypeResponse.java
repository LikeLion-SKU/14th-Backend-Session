package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.ContractStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "ContractTypeResponse: 계약 응답 DTO")
public class ContractTypeResponse {

    @Schema(description = "계약 ID", example = "1")
    private Long contractId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "계약 상태", example = "BEFORE")
    private ContractStatus contractStatus;

    @Schema(description = "생성 시각", example = "2026-05-30T10:00:00")
    private LocalDateTime createdAt;
}
