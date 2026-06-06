package com.likelion.besession.domain.contract.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContractCreateRequest {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "계약서 내용", example = "본 계약서는 임대차 계약에 관한 내용입니다.")
    private String content;

    @Schema(description = "계약 주소", example = "서울시 강남구 테헤란로 123")
    private String address;
}
