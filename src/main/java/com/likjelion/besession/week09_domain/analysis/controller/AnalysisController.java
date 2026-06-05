package com.likjelion.besession.week09_domain.analysis.controller;

import com.likjelion.besession.global.common.BaseResponse;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.week09_domain.analysis.dto.response.AnalysisResultResponse;
import com.likjelion.besession.week09_domain.analysis.dto.response.AnalysisUploadResponse;
import com.likjelion.besession.week09_domain.analysis.service.AnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Analysis", description = "계약서 분석 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/{contractId}/analysis")
public class AnalysisController {

    private final AnalysisService analysisService;

    @Operation(summary = "계약서 업로드 및 분석 요청", description = "계약서 이미지 파일을 업로드하고 분석을 요청합니다. 현재 구현에서는 실제 OCR/AI 연동 없이 더미 분석 결과와 리포트를 생성합니다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<AnalysisUploadResponse>> uploadAndAnalyze(
            @PathVariable Long contractId,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "분석 요청 성공", analysisService.uploadAndAnalyze(contractId, file, userDetails)));
    }

    @Operation(summary = "분석 결과 조회", description = "특정 계약의 계약서 분석 결과를 조회합니다. 계약서 원문 내용, AI 해석, 유의사항을 확인할 수 있습니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<AnalysisResultResponse>> getAnalysis(
            @PathVariable Long contractId) {
        return ResponseEntity.ok(BaseResponse.success(200, "분석 결과 조회 성공", analysisService.getAnalysis(contractId)));
    }
}
