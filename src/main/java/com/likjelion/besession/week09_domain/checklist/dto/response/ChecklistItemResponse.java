package com.likjelion.besession.week09_domain.checklist.dto.response;

import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChecklistItemResponse {
    private Long contractChecklistId;
    private Long checklistId;
    private String title;
    private String content;
    private ContractStatus contractStatus;
    private boolean checked;
}
