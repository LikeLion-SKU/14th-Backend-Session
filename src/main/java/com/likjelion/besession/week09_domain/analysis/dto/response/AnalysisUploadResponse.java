package com.likjelion.besession.week09_domain.analysis.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnalysisUploadResponse {
    private Long analysisId;
    private Long contractId;
    private String status;
    private LocalDateTime createdAt;
}
