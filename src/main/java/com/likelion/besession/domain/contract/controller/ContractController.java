package com.likelion.besession.domain.contract.controller;

import com.likelion.besession.domain.contract.dto.ContractResponse;
import com.likelion.besession.domain.contract.dto.ContractSaveRequest;
import com.likelion.besession.domain.contract.service.ContractService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
@Tag(name = "Contract", description = "계약 관련 API")
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/me")
    @Operation(summary = "내 계약 조회", description = "로그인한 사용자의 계약 정보를 조회합니다.")
    public BaseResponse<ContractResponse> getMyContract(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return BaseResponse.success(contractService.getMyContract(userDetails.getUser()));
    }

    @PutMapping("/me")
    @Operation(summary = "내 계약 저장 및 수정", description = "계약이 없으면 새로 등록하고, 이미 있으면 기존 계약 정보를 수정합니다.")
    public BaseResponse<Void> saveMyContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody ContractSaveRequest request
    ) {
        contractService.saveMyContract(userDetails.getUser(), request);
        return BaseResponse.success("계약 저장 성공", null);
    }
}