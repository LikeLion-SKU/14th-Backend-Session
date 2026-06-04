package com.likelion.besession.domain.contract.controller;

import com.likelion.besession.domain.contract.dto.response.ContractCreateResponse;
import com.likelion.besession.domain.contract.dto.response.ContractListResponse;
import com.likelion.besession.domain.contract.dto.response.ContractTypeResponse;
import com.likelion.besession.domain.contract.service.ContractService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Contract", description = "계약 관련 API")
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "계약 생성", description = "새로운 계약을 생성하는 API")
    @PostMapping("/contracts")
    public ResponseEntity<BaseResponse<ContractCreateResponse>> createContract(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        ContractCreateResponse response = contractService.createContract(userDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약이 생성되었습니다.", response));
    }

    @Operation(summary = "계약 전체 목록 조회", description = "로그인한 사용자의 전체 계약 목록을 조회하는 API")
    @GetMapping("/contracts")
    public ResponseEntity<BaseResponse<List<ContractListResponse>>> allContract(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ContractListResponse> response = contractService.allContract(userDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약 목록 조회에 성공했습니다.", response));
    }

    @Operation(summary = "계약 단건 조회", description = "계약 ID로 특정 계약을 조회하는 API")
    @GetMapping("/{contract-id}")
    public ResponseEntity<BaseResponse<ContractTypeResponse>> lookContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("contract-id") Long contractId) {
        ContractTypeResponse response = contractService.lookContract(userDetails.getUserId(), contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약 단건 조회에 성공했습니다.", response));
    }

    @Operation(summary = "계약 삭제", description = "계약을 삭제하는 API")
    @DeleteMapping("/{contract-id}")
    public ResponseEntity<BaseResponse<Void>> deleteContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("contract-id") Long contractId) {
        contractService.deleteContract(userDetails.getUserId(), contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약이 삭제되었습니다.", null));
    }

    // 다음 단계 계약으로 넘어가기
    @Operation(summary = "계약 상태 진행", description = "현재 단계 체크리스트가 모두 완료된 경우 다음 단계로 진행하는 API (BEFORE → DURING → AFTER)")
    @PatchMapping("/contracts/{contract-id}/progress")
    public ResponseEntity<BaseResponse<ContractTypeResponse>> progressContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("contract-id") Long contractId) {
        ContractTypeResponse response = contractService.nextContract(userDetails.getUserId(), contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약 상태가 변경되었습니다.", response));
    }
}
