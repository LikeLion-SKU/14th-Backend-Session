package com.example.likelionbe.domain.contract.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "CreateContractReqDto: 계약 신청 요청 DTO")
public record CreateContractReqDto(
        @Schema(description = "요청 메시지", example = "계약을 신청합니다.")
        String requestMessage
) {
}
