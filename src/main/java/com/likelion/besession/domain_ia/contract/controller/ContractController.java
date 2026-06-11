package com.likelion.besession.domain_ia.contract.controller;

import com.likelion.besession.domain_ia.contract.dto.request.ContractCreateRequest;
import com.likelion.besession.domain_ia.contract.dto.response.ContractAnalyzationResponse;
import com.likelion.besession.domain_ia.contract.dto.response.ContractCreateResponse;
import com.likelion.besession.domain_ia.contract.dto.response.ContractDetailResponse;
import com.likelion.besession.domain_ia.contract.service.ContractService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contract", description = "계약서 관련 API")
@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "계약서 생성", description = "계약서 등록 API")
    @PostMapping("/")
    public ResponseEntity<BaseResponse<ContractCreateResponse>> createContract(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody ContractCreateRequest contractCreateRequest
            ){
        ContractCreateResponse contractCreateResponse = contractService.createContract(contractCreateRequest, customUserDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약서 등록 완료", contractCreateResponse));
    }

    @Operation(summary = "계약서 조회", description = "계약서 ID 기반 조회 API")
    @GetMapping("/{contractId}")
    public ResponseEntity<BaseResponse<ContractDetailResponse>> getContract(@PathVariable Long contractId){
        ContractDetailResponse contractDetailResponse = contractService.getContractDetail(contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(contractDetailResponse));
    }

    @Operation(summary = "유저 계약서 리스트 조회", description = "유저의 모든 계약서 조회 API")
    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<ContractDetailResponse>>> getUserContracts(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        List<ContractDetailResponse> contractDetailResponseList = contractService.getUserContracts(customUserDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(contractDetailResponseList));
    }

    @Operation(summary = "현재 진행중인 계약 조회", description = "유저의 현재 진행중인 계약 조회 API")
    @GetMapping("/current")
    public ResponseEntity<BaseResponse<ContractDetailResponse>> getCurrentContract(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        ContractDetailResponse contractDetailResponse = contractService.getContractDetailOnGoing(customUserDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(contractDetailResponse));
    }

    @Operation(summary = "계약서 분석 확인", description = "계약서 상세 분석 확인")
    @GetMapping("/{contractId}/analysis")
    public ResponseEntity<BaseResponse<ContractAnalyzationResponse>> getContractAnalysis(@PathVariable Long contractId){
        ContractAnalyzationResponse  contractAnalyzationResponse = contractService.getContractAnalyzation(contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(contractAnalyzationResponse));
    }

//    @GetMapping("/contracts/{user_id}")
//    public ResponseEntity<Contract> getContractByUserId(@PathVariable String userId) {
//
//    }
//
//    @GetMapping("/contracts/{user_id}")
//    public ResponseEntity<Contract> getContractByUserId(@Parameter boolean isDone, @PathVariable String userId) {
//
//    }
}
