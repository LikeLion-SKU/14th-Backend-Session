package com.likelion.besession.domain.checklist.dto.response;

import com.likelion.besession.domain.checklist.entity.CheckListStatus;
import com.likelion.besession.domain.contract.entity.ContractStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "CheckListResponse: 체크리스트 응답 DTO")
public class CheckListResponse {

    @Schema(description = "체크리스트 ID", example = "1")
    private Long checkListId;

    @Schema(description = "계약 ID", example = "1")
    private Long contractId;

    @Schema(description = "계약 단계", example = "BEFORE")
    private ContractStatus phase;

    @Schema(description = "제목", example = "등기부등본 확인")
    private String title;

    @Schema(description = "내용", example = "대법원 인터넷등기소(iros.go.kr)에서 확인")
    private String checkListContent;

    @Schema(description = "상태", example = "INCOMPLETE")
    private CheckListStatus checkListStatus;

    @Schema(description = "생성 시각", example = "2026-05-30T10:00:00")
    private LocalDateTime createdAt;
}
