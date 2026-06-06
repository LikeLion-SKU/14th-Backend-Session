package com.likelion.besession.domain.report.dto.response;

import com.likelion.besession.domain.report.entity.AiReport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AiReportResponse {

    @Schema(description = "AI 분석 내용", example = "계약서 분석 결과 특이사항이 없습니다.")
    private String aiInterpretation;

    @Schema(description = "유의사항", example = "계약 만료 3개월 전 갱신 여부를 확인하세요.")
    private String precaution;

    public static AiReportResponse from(AiReport report) {
        return AiReportResponse.builder()
            .aiInterpretation(report.getAiInterpretation())
            .precaution(report.getPrecaution())
            .build();
    }
}
