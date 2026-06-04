package com.likelion.besession.domain.contract.controller;

import com.likelion.besession.domain.contract.dto.request.CreateAnalyseRequest;
import com.likelion.besession.domain.contract.dto.response.AnalyseResponse;
import com.likelion.besession.domain.contract.service.AnalyseService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Analyse", description = "계약서 AI 분석 및 리포트 API")
public class AnalyseController {

    private final AnalyseService analyseService;

    @Operation(summary = "계약서 사진 분석 요청", description = "계약서 사진 URL을 보내면 AI가 분석하여 리포트를 생성하고 저장합니다.")
    @PostMapping("/analyses")
    public ResponseEntity<BaseResponse<AnalyseResponse>> createAnalyse(@Valid @RequestBody CreateAnalyseRequest request) {
        AnalyseResponse response = analyseService.createAnalyse(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(201, "계약서 분석이 완료되었습니다.", response));
    }

    @Operation(summary = "내 분석 리포트 목록 조회", description = "특정 사용자가 저장한 모든 계약 리포트 기록을 최신순으로 조회합니다.")
    @GetMapping("/users/{user-id}/analyses")
    public ResponseEntity<BaseResponse<List<AnalyseResponse>>> getMyAnalyses(@PathVariable("user-id") Long userId) {
        List<AnalyseResponse> response = analyseService.getMyAnalyses(userId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(response));
    }

    @Operation(summary = "분석 리포트 상세 조회", description = "리포트 ID를 통해 특정 계약서 분석 결과를 상세 조회합니다.")
    @GetMapping("/analyses/{analyse-id}")
    public ResponseEntity<BaseResponse<AnalyseResponse>> getAnalyseById(@PathVariable("analyse-id") Long analyseId) {
        AnalyseResponse response = analyseService.getAnalyseById(analyseId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(response));
    }
}