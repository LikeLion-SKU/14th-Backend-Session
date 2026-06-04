package com.likelion.besession.domain.contractdocs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "ContractDocsAnalyzeResponse: 계약서 단건 조회 응답 DTO")
public class ContractDocsAnalyzeResponse {

    @Schema(description = "계약서 ID", example = "1")
    private Long contractDocsId;

    @Schema(description = "계약 ID", example = "1")
    private Long contractId;

    @Schema(description = "계약서 제목", example = "서경로 300 2층 계약서")
    private String title;

    @Schema(description = "계약서 촬영 이미지 URL", example = "https://example.com/images/contract.jpg")
    private String contractImage;

    @Schema(description = "계약서 내용", example = "임차인이 2개월 이상 차임을 연체할 경우 임대인은 즉시 계약을 해지할 수 있다.")
    private String content;

    @Schema(description = "AI 해석", example = "월세를 2달 못 내면 집주인이 바로 계약을 끊을 수 있다는 뜻이에요.")
    private String aiTranslation;

    @Schema(description = "유의사항", example = "월세 납부일과 방법을 명시해두면 나중에 분쟁을 예방할 수 있어요.")
    private String note;

    @Schema(description = "생성 시각", example = "2026-05-30T14:00:00")
    private LocalDateTime createdAt;
}
