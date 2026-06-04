package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.ContractAnalysis;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ContractAnalysisResponse: AI 분석 결과 응답 DTO")
public class ContractAnalysisResponse {

    @Schema(description = "분석 ID", example = "1")
    private Long id;

    @Schema(description = "계약서 ID", example = "1")
    private Long contractId;

    @Schema(description = "AI 요약")
    private String aiSummary;

    @Schema(description = "AI 내용 분석")
    private String aiContents;

    @Schema(description = "AI 위험 이슈 목록")
    private String aiIssues;

    @Schema(description = "AI 위험도 점수 (0~100)", example = "35")
    private Integer aiScore;

    public static ContractAnalysisResponse from(ContractAnalysis analysis) {
        return ContractAnalysisResponse.builder()
                .id(analysis.getId())
                .contractId(analysis.getContract().getId())
                .aiSummary(analysis.getAiSummary())
                .aiContents(analysis.getAiContents())
                .aiIssues(analysis.getAiIssues())
                .aiScore(analysis.getAiScore())
                .build();
    }
}
