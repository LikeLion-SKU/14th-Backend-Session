package com.likelion.besession.domain.schedule.dto.response;

import com.likelion.besession.domain.schedule.entity.CheckList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "CheckListResponse: 체크리스트 응답 DTO")
public class CheckListResponse {

    @Schema(description = "체크리스트 ID", example = "1")
    private Long id;

    @Schema(description = "체크리스트 항목 제목", example = "계약서 원본 확인")
    private String title;

    @Schema(description = "완료 여부", example = "false")
    private boolean isChecked;

    public static CheckListResponse from(CheckList checkList) {
        return CheckListResponse.builder()
                .id(checkList.getId())
                .title(checkList.getTitle())
                .isChecked(checkList.isChecked())
                .build();
    }
}
