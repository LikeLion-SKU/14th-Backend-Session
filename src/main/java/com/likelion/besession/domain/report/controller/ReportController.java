package com.likelion.besession.domain.report.controller;

import com.likelion.besession.domain.report.dto.response.AiReportResponse;
import com.likelion.besession.domain.report.service.ReportService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contracts")
@Tag(name = "Report", description = "AI 분석 관련 API")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "(AI) AI 분석 결과 조회", description = "계약 ID에 해당하는 AI 분석 내용과 유의사항을 조회")
    @GetMapping("/{id}/analysis")
    public ResponseEntity<BaseResponse<AiReportResponse>> getAnalysis(@PathVariable Long id) {
        return ResponseEntity.ok(BaseResponse.success(reportService.getAnalysis(id)));
    }
}
