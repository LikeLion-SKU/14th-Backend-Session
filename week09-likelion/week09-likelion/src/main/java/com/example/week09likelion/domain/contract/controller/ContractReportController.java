package com.example.week09likelion.domain.contract.controller;

import com.example.week09likelion.domain.contract.dto.response.ContractReportResponse;
import com.example.week09likelion.domain.contract.service.ContractReportService;
import com.example.week09likelion.domain.user.exception.UserErrorCode;
import com.example.week09likelion.global.common.BaseResponse;
import com.example.week09likelion.global.exception.CustomException;
import com.example.week09likelion.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Contract Report", description = "계약서 분석 기록 관련 API")
public class ContractReportController {

    private final ContractReportService contractReportService;

    @Operation(summary = "계약서 분석 기록 조회", description = "기본 계약서 내용, 해석 텍스트, 유의사항 텍스트를 조회하는 API")
    @GetMapping("/api/contracts/report")
    public ResponseEntity<BaseResponse<ContractReportResponse>> getContractReport(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        ContractReportResponse contractReportResponse = contractReportService.getContractReport();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약서 분석 기록 조회 성공", contractReportResponse));
    }
}