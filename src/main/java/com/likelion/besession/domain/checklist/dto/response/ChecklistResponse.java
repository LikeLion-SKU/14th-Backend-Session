package com.likelion.besession.domain.checklist.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "ChecklistResponse: 체크리스트 응답 DTO")
public record ChecklistResponse(

    @Schema(description = "체크리스트 항목 ID", example = "1")
    Long taskId,

    @Schema(description = "단계", example = "BEFORE")
    String stage,

    @Schema(description = "항목명", example = "등기부등본 확인")
    String taskName,

    @Schema(description = "항목 설명", example = "등기부등본에서 소유자 확인")
    String description,

    @Schema(description = "정렬 순서", example = "1")
    Integer checklistOrder,

    @Schema(description = "체크 여부", example = "false")
    Boolean isChecked,

    @Schema(description = "생성일시")
    LocalDateTime createdAt
) {}
