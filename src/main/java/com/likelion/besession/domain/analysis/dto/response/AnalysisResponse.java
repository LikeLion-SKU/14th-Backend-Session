package com.likelion.besession.domain.analysis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "AnalysisResponse: 분석 상세 응답 DTO")
public record AnalysisResponse(

    @Schema(description = "분석 ID", example = "1")
    Long analysisId,

    @Schema(description = "계약서 원문 내용")
    String contractContent,

    @Schema(description = "AI 해석 결과")
    String aiInterpretation,

    @Schema(description = "유의사항")
    String precautions,

    @Schema(description = "계약서 이미지 URL")
    String imageUrl,

    @Schema(description = "생성일시")
    LocalDateTime createdAt
) {}
