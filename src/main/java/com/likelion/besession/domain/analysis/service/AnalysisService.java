package com.likelion.besession.domain.analysis.service;

import com.likelion.besession.domain.analysis.dto.AnalysisRequest;
import com.likelion.besession.domain.analysis.dto.AnalysisResponse;
import org.springframework.stereotype.Service;

@Service
public class AnalysisService {

    public AnalysisResponse analyze(AnalysisRequest request) {
        return new AnalysisResponse(
                request.getContractContent(),
                "이 조항은 계약서의 주요 내용을 쉽게 풀어 설명한 임시 해석입니다.",
                "계약 기간, 보증금, 월세, 특약 사항을 반드시 확인하세요."
        );
    }
}