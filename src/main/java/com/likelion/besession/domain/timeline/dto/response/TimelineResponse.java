package com.likelion.besession.domain.timeline.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(title = "TimelineResponse: 타임라인 응답 DTO")
public record TimelineResponse(

    @Schema(description = "타임라인 ID", example = "1")
    Long timelineId,

    @Schema(description = "타임라인 제목", example = "계약금 납부")
    String title,

    @Schema(description = "단계", example = "BEFORE")
    String stage,

    @Schema(description = "정렬 순서", example = "1")
    Integer timelineOrder,

    @Schema(description = "생성일시")
    LocalDateTime createdAt
) {}
