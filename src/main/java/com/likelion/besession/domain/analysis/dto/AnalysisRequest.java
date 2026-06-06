package com.likelion.besession.domain.analysis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "계약서 분석 요청 DTO")
public class AnalysisRequest {

    @Schema(description = "계약서 내용", example = "임차인이 2개월 이상 차임을 연체할 경우 임대인은 계약을 해지할 수 있다.")
    private String contractContent;
}