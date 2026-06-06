package com.likelion.besession.domain.contract.controller;

import com.likelion.besession.domain.contract.dto.request.CreateContractRequest;
import com.likelion.besession.domain.contract.dto.request.UpdateContractStatusRequest;
import com.likelion.besession.domain.contract.dto.response.ContractResponse;
import com.likelion.besession.domain.contract.service.ContractService;
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
@RequestMapping("/api/contracts")
@Tag(name = "Contract", description = "계약 관련 API")
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "계약 생성", description = "주소를 입력받아 계약 생성 (유저당 1개)")
    @PostMapping
    public ResponseEntity<BaseResponse<ContractResponse>> createContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateContractRequest request) {
        ContractResponse response = contractService.createContract(userDetails.getUser().getId(), request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success("계약 생성에 성공했습니다.", response));
    }

    @Operation(summary = "내 계약 조회", description = "본인 계약 정보 조회")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<ContractResponse>> getMyContract(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        ContractResponse response = contractService.getMyContract(userDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("계약 조회에 성공했습니다.", response));
    }

    @Operation(summary = "계약 상태 변경", description = "계약 상태(전/중/후) 변경")
    @PatchMapping("/{contractId}/status")
    public ResponseEntity<BaseResponse<ContractResponse>> updateContractStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId,
            @Valid @RequestBody UpdateContractStatusRequest request) {
        ContractResponse response = contractService.updateContractStatus(
                userDetails.getUser().getId(), contractId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("계약 상태 변경에 성공했습니다.", response));
    }
}
