package com.likelion.besession.domain.checklist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "체크리스트 수정 요청 DTO")
public class ChecklistUpdateRequest {

    @Schema(description = "체크 여부", example = "true")
    private Boolean checked;
}