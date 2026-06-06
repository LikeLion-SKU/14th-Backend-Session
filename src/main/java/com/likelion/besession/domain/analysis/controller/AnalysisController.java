package com.likelion.besession.domain.analysis.controller;

import com.likelion.besession.domain.analysis.dto.response.AnalysisResponse;
import com.likelion.besession.domain.analysis.dto.response.AnalysisSummaryResponse;
import com.likelion.besession.domain.analysis.service.AnalysisService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Analysis", description = "계약서 분석 관련 API")
public class AnalysisController {

    private final AnalysisService analysisService;

    @Operation(summary = "계약서 분석", description = "계약서 사진을 AI로 분석해 내용·해석·유의사항 생성")
    @PostMapping(value = "/api/contracts/{contractId}/analyses", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse<AnalysisResponse>> createAnalysis(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId,
            @RequestPart MultipartFile image) {
        AnalysisResponse response = analysisService.createAnalysis(
                userDetails.getUser().getId(), contractId, image);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success("계약서 분석에 성공했습니다.", response));
    }

    @Operation(summary = "분석 목록 조회", description = "계약의 분석 결과 목록 조회 (최신순)")
    @GetMapping("/api/contracts/{contractId}/analyses")
    public ResponseEntity<BaseResponse<List<AnalysisSummaryResponse>>> getAnalyses(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        List<AnalysisSummaryResponse> response = analysisService.getAnalyses(
                userDetails.getUser().getId(), contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("분석 목록 조회에 성공했습니다.", response));
    }

    @Operation(summary = "분석 상세 조회", description = "분석 결과 상세 조회 (내용·해석·유의사항)")
    @GetMapping("/api/analyses/{analysisId}")
    public ResponseEntity<BaseResponse<AnalysisResponse>> getAnalysis(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long analysisId) {
        AnalysisResponse response = analysisService.getAnalysis(
                userDetails.getUser().getId(), analysisId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("분석 상세 조회에 성공했습니다.", response));
    }

    @Operation(summary = "분석 삭제", description = "분석 결과 삭제")
    @DeleteMapping("/api/analyses/{analysisId}")
    public ResponseEntity<BaseResponse<Void>> deleteAnalysis(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long analysisId) {
        analysisService.deleteAnalysis(userDetails.getUser().getId(), analysisId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("분석 삭제에 성공했습니다.", null));
    }
}
