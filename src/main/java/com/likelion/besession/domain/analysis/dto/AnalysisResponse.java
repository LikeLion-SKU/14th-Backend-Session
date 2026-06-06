package com.likelion.besession.domain.analysis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnalysisResponse {

    private String contractContent;
    private String aiSummary;
    private String caution;
}