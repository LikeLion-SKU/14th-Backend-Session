package com.likelion.besession.domain.contractdocs.controller;

import com.likelion.besession.domain.contractdocs.dto.request.ContractDocsAnalyzeRequest;
import com.likelion.besession.domain.contractdocs.dto.response.ContractDocsAnalyzeResponse;
import com.likelion.besession.domain.contractdocs.service.ContractDocsService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "ContractDocs", description = "계약서 관련 API")
public class ContractDocsController {

    private final ContractDocsService contractDocsService;

    @Operation(summary = "계약서 분석", description = "계약서를 업로드하고 AI 분석 결과를 반환하는 API")
    @PostMapping("/contracts/{contract-id}/docs")
    public ResponseEntity<BaseResponse<ContractDocsAnalyzeResponse>> createContractDocs(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("contract-id") Long contractId,
            @Valid @RequestBody ContractDocsAnalyzeRequest request) {
        ContractDocsAnalyzeResponse response = contractDocsService.analyzeContractDocs(userDetails.getUserId(), contractId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약서가 생성되었습니다.", response));
    }

    @Operation(summary = "계약서 분석 단건 조회", description = "해당 계약의 계약서를 분석한 결과를 조회하는 API")
    @GetMapping("/contracts/{contract-id}/docs")
    public ResponseEntity<BaseResponse<ContractDocsAnalyzeResponse>> lookContractDocs(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("contract-id") Long contractId) {
        ContractDocsAnalyzeResponse response = contractDocsService.lookContractDocs(userDetails.getUserId(), contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약서 조회에 성공했습니다.", response));
    }

    @Operation(summary = "계약서 분석 결과 삭제", description = "계약서를 분석 결과를 삭제하는 API")
    @DeleteMapping("/contracts/{contract-id}/docs")
    public ResponseEntity<BaseResponse<Void>> deleteContractDocs(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("contract-id") Long contractId) {
        contractDocsService.deleteContractDocs(userDetails.getUserId(), contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약서가 삭제되었습니다.", null));
    }
}
