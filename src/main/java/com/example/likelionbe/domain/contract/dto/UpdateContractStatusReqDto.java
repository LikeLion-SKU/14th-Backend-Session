package com.example.likelionbe.domain.contract.dto;

import com.example.likelionbe.domain.contract.enums.ContractStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "UpdateContractStatusReqDto: 계약 상태 변경 요청 DTO")
public record UpdateContractStatusReqDto(
        @Schema(description = "변경할 계약 상태", example = "APPROVED")
        ContractStatus contractStatus
) {
}
