package com.likelion.besession.domain.contract.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(title = "CreateContractRequest: 계약 생성 요청 DTO")
public record CreateContractRequest(

    @NotBlank(message = "주소는 필수입니다.")
    @Schema(description = "계약 주소", example = "서울시 강남구 역삼동 123-45")
    String address
) {}
