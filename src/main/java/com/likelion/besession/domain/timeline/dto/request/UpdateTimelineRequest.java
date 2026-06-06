package com.likelion.besession.domain.timeline.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(title = "UpdateTimelineRequest: 타임라인 수정 요청 DTO")
public record UpdateTimelineRequest(

    @NotBlank(message = "타임라인 제목은 필수입니다.")
    @Schema(description = "타임라인 제목", example = "계약금 납부")
    String title,

    @NotBlank(message = "단계는 필수입니다.")
    @Schema(description = "단계 (BEFORE, DURING, AFTER)", example = "DURING")
    String stage,

    @NotNull(message = "정렬 순서는 필수입니다.")
    @Schema(description = "정렬 순서", example = "2")
    Integer timelineOrder
) {}
