package com.likjelion.besession.week09_domain.checklist.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChecklistUpdateResponse {
    private Long contractChecklistId;
    private boolean checked;
    private LocalDateTime updatedAt;
    private Long contractId;
    private int processRate;
}
