package com.likelion.besession.domain.contract.controller;

import com.likelion.besession.domain.contract.dto.request.CreateContractRequest;
import com.likelion.besession.domain.contract.dto.response.*;
import com.likelion.besession.domain.contract.service.ContractService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.config.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts")
@Tag(name = "Contract", description = "계약서 관련 API")
@SecurityRequirement(name = "bearerAuth")
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "계약서 생성", description = "새 계약서를 등록하는 API")
    @PostMapping
    public ResponseEntity<BaseResponse<ContractResponse>> createContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateContractRequest request) {
        ContractResponse response = contractService.createContract(userDetails.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약서가 등록되었습니다.", response));
    }

    @Operation(summary = "계약서 목록 조회", description = "내 계약서 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<BaseResponse<List<ContractResponse>>> getMyContracts(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ContractResponse> response = contractService.getMyContracts(userDetails.getUser().getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "계약서 상세 조회", description = "특정 계약서의 상세 정보를 조회하는 API")
    @GetMapping("/{contractId}")
    public ResponseEntity<BaseResponse<ContractResponse>> getContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        ContractResponse response = contractService.getContract(userDetails.getUser().getId(), contractId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "계약서 내용 조회", description = "계약서 파일 및 계약 기간을 조회하는 API")
    @GetMapping("/{contractId}/content")
    public ResponseEntity<BaseResponse<ContractContentResponse>> getContractContent(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        ContractContentResponse response = contractService.getContractContent(userDetails.getUser().getId(), contractId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "계약서 이미지 조회", description = "계약서 문서 파일 URL을 조회하는 API")
    @GetMapping("/{contractId}/images")
    public ResponseEntity<BaseResponse<ContractContentResponse>> getContractImage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        ContractContentResponse response = contractService.getContractImage(userDetails.getUser().getId(), contractId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "계약 당사자 조회", description = "계약서의 계약자 및 상대방 정보를 조회하는 API")
    @GetMapping("/{contractId}/parties")
    public ResponseEntity<BaseResponse<ContractPartiesResponse>> getContractParties(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        ContractPartiesResponse response = contractService.getContractParties(userDetails.getUser().getId(), contractId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "AI 분석 요청", description = "계약서 AI 위험도 분석을 요청하는 API")
    @PostMapping("/{contractId}/analyze")
    public ResponseEntity<BaseResponse<ContractAnalysisResponse>> analyzeContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        ContractAnalysisResponse response = contractService.analyzeContract(userDetails.getUser().getId(), contractId);
        return ResponseEntity.ok(BaseResponse.success("AI 분석이 완료되었습니다.", response));
    }

    @Operation(summary = "AI 분석 결과 조회", description = "계약서 AI 분석 결과를 조회하는 API")
    @GetMapping("/{contractId}/analyses")
    public ResponseEntity<BaseResponse<ContractAnalysisResponse>> getAnalysis(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId) {
        ContractAnalysisResponse response = contractService.getAnalysis(userDetails.getUser().getId(), contractId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}
