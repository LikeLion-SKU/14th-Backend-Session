package com.likjelion.besession.week09_domain.contract.controller;

import com.likjelion.besession.global.common.BaseResponse;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.week09_domain.contract.dto.request.CreateContractRequest;
import com.likjelion.besession.week09_domain.contract.dto.request.UpdateContractStatusRequest;
import com.likjelion.besession.week09_domain.contract.dto.response.*;
import com.likjelion.besession.week09_domain.contract.service.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contract", description = "계약 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "내 현재 계약 조회", description = "현재 로그인한 사용자의 진행 중이거나 가장 최근 계약 정보를 조회합니다. 홈 화면과 일정 화면에서 현재 계약 상태, 주소, 진행률을 표시할 때 사용합니다.")
    @GetMapping("/current")
    public ResponseEntity<BaseResponse<CurrentContractResponse>> getCurrentContract(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(BaseResponse.success(200, "현재 계약 조회 성공", contractService.getCurrentContract(userDetails)));
    }

    @Operation(summary = "새 계약 등록", description = "새로운 계약 정보를 등록합니다. 주소와 매물 유형을 입력받아 Property와 Contract를 생성하고 현재 사용자와 연결합니다.")
    @PostMapping
    public ResponseEntity<BaseResponse<CreateContractResponse>> createContract(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CreateContractRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약 등록 성공", contractService.createContract(userDetails, request)));
    }

    @Operation(summary = "계약 상세 조회", description = "특정 계약의 상세 정보를 조회합니다. 계약 상태, 진행률, 매물 주소, 매물 유형, 생성일과 수정일을 확인할 수 있습니다.")
    @GetMapping("/{contractId}")
    public ResponseEntity<BaseResponse<ContractDetailResponse>> getContractDetail(
            @PathVariable Long contractId) {
        return ResponseEntity.ok(BaseResponse.success(200, "계약 상세 조회 성공", contractService.getContractDetail(contractId)));
    }

    @Operation(summary = "계약 단계 변경", description = "계약의 진행 단계를 변경합니다. 계약 전, 계약 중, 계약 후 상태로 변경할 수 있습니다.")
    @PatchMapping("/{contractId}/status")
    public ResponseEntity<BaseResponse<ContractStatusUpdateResponse>> updateContractStatus(
            @PathVariable Long contractId,
            @RequestBody UpdateContractStatusRequest request) {
        return ResponseEntity.ok(BaseResponse.success(200, "계약 단계 변경 성공", contractService.updateContractStatus(contractId, request)));
    }

    @Operation(summary = "계약 목록 조회", description = "현재 로그인한 사용자가 등록한 계약 목록을 조회합니다. 마이페이지의 계약 기록 또는 리포트 기록 진입에 사용할 수 있습니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<List<ContractListItemResponse>>> getContractList(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(BaseResponse.success(200, "계약 목록 조회 성공", contractService.getContractList(userDetails)));
    }
}
