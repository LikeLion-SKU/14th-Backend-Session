package com.likelion.besession.domain.contract.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "ContractResponse: 계약 응답 DTO")
public record ContractResponse(

    @Schema(description = "계약 ID", example = "1")
    Long contractId,

    @Schema(description = "주소", example = "서울시 강남구 역삼동 123-45")
    String address,

    @Schema(description = "계약 상태", example = "BEFORE")
    String status,

    @Schema(description = "생성일시")
    LocalDateTime createdAt
) {}
