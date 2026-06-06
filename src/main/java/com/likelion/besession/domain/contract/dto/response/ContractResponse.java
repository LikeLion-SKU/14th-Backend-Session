package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.Contract;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractResponse {

    @Schema(description = "계약 ID", example = "1")
    private Long contractId;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "계약서 내용", example = "본 계약서는 임대차 계약에 관한 내용입니다.")
    private String content;

    @Schema(description = "계약 주소", example = "서울시 강남구 테헤란로 123")
    private String address;

    @Schema(description = "진행률", example = "0")
    private int progressRate;

    @Schema(description = "계약 단계", example = "계약전")
    private String status;

    @Schema(description = "생성 날짜", example = "2024-01-01")
    private LocalDate createdAt;

    public static ContractResponse from(Contract contract) {
        return ContractResponse.builder()
            .contractId(contract.getContractId())
            .userId(contract.getUser().getId())
            .content(contract.getContent())
            .address(contract.getAddress())
            .progressRate(contract.getProgressRate())
            .status(contract.getStatus())
            .createdAt(contract.getCreatedAt())
            .build();
    }
}
