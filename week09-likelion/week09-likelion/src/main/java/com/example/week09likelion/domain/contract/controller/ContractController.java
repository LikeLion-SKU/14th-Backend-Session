package com.example.week09likelion.domain.contract.controller;

import com.example.week09likelion.domain.contract.dto.request.ContractRequest;
import com.example.week09likelion.domain.contract.dto.response.ContractResponse;
import com.example.week09likelion.domain.contract.service.ContractService;
import com.example.week09likelion.domain.user.exception.UserErrorCode;
import com.example.week09likelion.global.common.BaseResponse;
import com.example.week09likelion.global.exception.CustomException;
import com.example.week09likelion.security.CustomUserDetails;
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

    @Operation(summary = "내 계약 조회", description = "로그인한 사용자가 등록한 계약 정보를 조회하는 API")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<ContractResponse>> getMyContract(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        ContractResponse contractResponse = contractService.getMyContract(
                customUserDetails.getUser().getId()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약 조회 성공", contractResponse));
    }

    @Operation(summary = "계약 등록", description = "로그인한 사용자의 계약 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<BaseResponse<ContractResponse>> createContract(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody ContractRequest request
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        ContractResponse contractResponse = contractService.createContract(
                customUserDetails.getUser().getId(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약 등록 성공", contractResponse));
    }

    @Operation(summary = "계약 수정", description = "로그인한 사용자의 계약 제목, 주소, 상태, 진행률을 수정하는 API")
    @PutMapping("/me")
    public ResponseEntity<BaseResponse<ContractResponse>> updateMyContract(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody ContractRequest request
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        ContractResponse contractResponse = contractService.updateMyContract(
                customUserDetails.getUser().getId(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약 수정 성공", contractResponse));
    }

    @Operation(summary = "계약 삭제", description = "로그인한 사용자의 계약 정보를 삭제하는 API")
    @DeleteMapping("/me")
    public ResponseEntity<BaseResponse<Void>> deleteMyContract(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        contractService.deleteMyContract(customUserDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약 삭제 성공", null));
    }
}