package com.likelion.besession.domain.checklist.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(title = "UpdateChecklistRequest: 체크리스트 수정 요청 DTO")
public record UpdateChecklistRequest(

    @NotNull(message = "체크 여부는 필수입니다.")
    @Schema(description = "체크 여부", example = "true")
    Boolean isChecked
) {}
