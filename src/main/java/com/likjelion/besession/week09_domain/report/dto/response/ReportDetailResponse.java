package com.likjelion.besession.week09_domain.report.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReportDetailResponse {
    private Long reportId;
    private Long contractId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
