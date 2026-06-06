package com.example.likelionbe.domain.contract.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(title = "UpdateChecklistReqDto: 체크리스트 수정 요청 DTO")
public record UpdateChecklistReqDto(
        @Schema(description = "체크리스트 항목 목록")
        List<UpdateChecklistItemReqDto> items
) {
    public record UpdateChecklistItemReqDto(
            @Schema(description = "체크리스트 항목 ID", example = "1")
            Long checklistId,

            @Schema(description = "체크 여부", example = "true")
            Boolean checked
    ) {}
}
