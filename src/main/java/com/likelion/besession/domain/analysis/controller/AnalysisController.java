package com.likelion.besession.domain.analysis.controller;

import com.likelion.besession.domain.analysis.dto.AnalysisRequest;
import com.likelion.besession.domain.analysis.dto.AnalysisResponse;
import com.likelion.besession.domain.analysis.service.AnalysisService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts/me/analysis")
@RequiredArgsConstructor
@Tag(name = "Analysis", description = "계약서 분석 관련 API")
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping
    @Operation(summary = "계약서 분석", description = "입력된 계약서 내용을 바탕으로 임시 AI 해석 문자열과 유의사항을 반환합니다.")
    public BaseResponse<AnalysisResponse> analyze(@RequestBody AnalysisRequest request) {
        return BaseResponse.success(analysisService.analyze(request));
    }
}