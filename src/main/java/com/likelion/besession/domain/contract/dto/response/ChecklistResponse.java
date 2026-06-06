package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.Checklist;
import com.likelion.besession.domain.contract.entity.ContractPhase;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChecklistResponse {

    @Schema(description = "체크리스트 ID", example = "1")
    private Long checklistId;

    @Schema(description = "계약 ID", example = "1")
    private Long contractId;

    @Schema(description = "제목", example = "등기부등본 확인")
    private String title;

    @Schema(description = "내용", example = "임대인 명의와 등기부등본 일치 여부 확인")
    private String content;

    @Schema(description = "계약 단계", example = "계약전")
    private ContractPhase phase;

    @Schema(description = "체크 여부", example = "false")
    private boolean status;

    @Schema(description = "생성 날짜", example = "2024-01-01")
    private LocalDate createdAt;

    public static ChecklistResponse from(Checklist checklist) {
        return ChecklistResponse.builder()
            .checklistId(checklist.getChecklistId())
            .contractId(checklist.getContract().getContractId())
            .title(checklist.getTitle())
            .content(checklist.getContent())
            .phase(checklist.getPhase())
            .status(checklist.isStatus())
            .createdAt(checklist.getCreatedAt())
            .build();
    }
}
