package com.likelion.besession.domain.contractdocs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "ContractDocsListResponse: 계약서 목록 조회 응답 DTO")
public class ContractDocsListResponse {

    @Schema(description = "계약서 ID", example = "1")
    private Long contractDocsId;

    @Schema(description = "계약 ID", example = "1")
    private Long contractId;

    @Schema(description = "계약서 제목", example = "서경로 300 2층 계약서")
    private String title;

    @Schema(description = "계약서 촬영 이미지 URL", example = "https://example.com/images/contract.jpg")
    private String contractImage;

    @Schema(description = "생성 시각", example = "2026-05-30T14:00:00")
    private LocalDateTime createdAt;
}
