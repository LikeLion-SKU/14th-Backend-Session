package com.likjelion.besession.week09_domain.report.controller;

import com.likjelion.besession.global.common.BaseResponse;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.week09_domain.report.dto.response.ReportDetailResponse;
import com.likjelion.besession.week09_domain.report.dto.response.ReportListItemResponse;
import com.likjelion.besession.week09_domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Report", description = "리포트 API")
@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "리포트 조회", description = "현재 로그인한 사용자의 계약 분석 리포트 목록을 조회합니다. 마이페이지의 내 계약 리포트 기록에서 사용합니다.")
    @GetMapping("/api/reports")
    public ResponseEntity<BaseResponse<List<ReportListItemResponse>>> getMyReports(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(BaseResponse.success(200, "리포트 목록 조회 성공", reportService.getMyReports(userDetails)));
    }

    @Operation(summary = "계약별 리포트 상세 조회", description = "특정 계약에 연결된 리포트 상세 내용을 조회합니다. 리포트 제목, 본문 내용, 생성일을 확인할 수 있습니다.")
    @GetMapping("/api/contracts/{contractId}/report")
    public ResponseEntity<BaseResponse<ReportDetailResponse>> getContractReport(
            @PathVariable Long contractId) {
        return ResponseEntity.ok(BaseResponse.success(200, "리포트 상세 조회 성공", reportService.getContractReport(contractId)));
    }
}
