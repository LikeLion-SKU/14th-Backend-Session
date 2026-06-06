package com.likelion.besession.domain.contract.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(title = "UpdateContractStatusRequest: 계약 상태 변경 요청 DTO")
public record UpdateContractStatusRequest(

    @NotBlank(message = "상태값은 필수입니다.")
    @Schema(description = "계약 상태 (BEFORE, DURING, AFTER)", example = "DURING")
    String status
) {}
