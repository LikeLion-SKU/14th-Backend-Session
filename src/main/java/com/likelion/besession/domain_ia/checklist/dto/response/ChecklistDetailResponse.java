package com.likelion.besession.domain_ia.checklist.dto.response;

import com.likelion.besession.domain_ia.contract.entity.Process;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ChecklistDetailResponse : 체크리스트 상세 응답 DTO")
public class ChecklistDetailResponse {
    private Long contractChecklistStatusId;
    private Long checklistId;
    private String name;
    private String content;
    private Process process;
    private boolean isChecked;

}
