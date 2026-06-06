package com.likelion.besession.domain.analysis.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "AnalysisSummaryResponse: 분석 목록 응답 DTO")
public record AnalysisSummaryResponse(

    @Schema(description = "분석 ID", example = "1")
    Long analysisId,

    @Schema(description = "계약서 이미지 URL")
    String imageUrl,

    @Schema(description = "생성일시")
    LocalDateTime createdAt
) {}
