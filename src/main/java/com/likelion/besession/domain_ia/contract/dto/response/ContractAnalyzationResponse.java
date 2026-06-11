package com.likelion.besession.domain_ia.contract.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ContractAnalyzationResponse : 계약서 분석 결과 응답 DTO")
public class ContractAnalyzationResponse {

    @Schema(description = "분석 ID")
    private Long id;

    @Schema(description = "계약서 ID")
    private Long contractId;

    @Schema(description = "계약서 내용")
    private String content;

    @Schema(description = "AI 해석")
    private String aiAnalyzation;

    @Schema(description = "유의사항")
    private String notice;
}
