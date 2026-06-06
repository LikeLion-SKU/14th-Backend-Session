package com.example.likelionbe.domain.contract.dto;

import com.example.likelionbe.domain.contract.enums.ChecklistCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Schema(title = "ChecklistResDto: 체크리스트 응답 DTO")
public record ChecklistResDto(
        @Schema(description = "계약 ID", example = "1")
        Long contractId,

        @Schema(description = "체크리스트 항목 목록")
        List<ChecklistItemResDto> items
) {
    @Builder
    public record ChecklistItemResDto(
            @Schema(description = "체크리스트 항목 ID", example = "1")
            Long checklistId,

            @Schema(description = "체크리스트 코드", example = "VERIFY_PARTY_IDENTITY")
            ChecklistCode checklistCode,

            @Schema(description = "항목 제목", example = "계약 당사자 신원 확인")
            String title,

            @Schema(description = "항목 설명", example = "판매자/구매자 이름, 연락처, 신분 정보가 정확한지 확인하세요")
            String description,

            @Schema(description = "정렬 순서", example = "1")
            Integer sortOrder,

            @Schema(description = "필수 여부", example = "true")
            Boolean required,

            @Schema(description = "체크 여부", example = "false")
            Boolean checked,

            @Schema(description = "체크 일시", example = "2025-01-01T00:00:00")
            LocalDateTime checkedAt
    ) {}
}
