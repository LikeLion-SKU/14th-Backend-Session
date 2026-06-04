package com.likelion.besession.domain.contract.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class AnalyseResponse {
    private Long analyseId;
    private String imageUrl;
    private String originalText;
    private String aiInterpretation;
    private String warningText;
    private LocalDateTime createdAt;
}