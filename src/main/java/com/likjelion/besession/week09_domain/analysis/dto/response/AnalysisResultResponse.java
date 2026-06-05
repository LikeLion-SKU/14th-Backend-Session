package com.likjelion.besession.week09_domain.analysis.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnalysisResultResponse {
    private Long analysisId;
    private Long contractId;
    private String contractContent;
    private String aiComment;
    private String warning;
    private LocalDateTime createdAt;
}
