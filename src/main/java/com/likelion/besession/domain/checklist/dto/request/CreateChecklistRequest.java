package com.likelion.besession.domain.checklist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(title = "CreateChecklistRequest: 체크리스트 생성 요청 DTO")
public record CreateChecklistRequest(

    @NotBlank(message = "단계는 필수입니다.")
    @Schema(description = "단계 (BEFORE, DURING, AFTER)", example = "BEFORE")
    String stage,

    @NotBlank(message = "항목명은 필수입니다.")
    @Schema(description = "체크리스트 항목명", example = "등기부등본 확인")
    String taskName,

    @Schema(description = "항목 설명", example = "등기부등본에서 소유자 확인")
    String description,

    @NotNull(message = "정렬 순서는 필수입니다.")
    @Schema(description = "정렬 순서", example = "1")
    Integer checklistOrder
) {}
