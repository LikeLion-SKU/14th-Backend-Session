package com.example.likelionbe.domain.contract.controller;

import com.example.likelionbe.domain.contract.dto.*;
import com.example.likelionbe.global.common.BaseResponse;
import com.example.likelionbe.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Contract", description = "계약 관련 API")
@RequestMapping("/api")
public interface ContractControllerDocs {

    @Operation(summary = "계약 신청", description = "구매자가 특정 매물에 계약을 신청합니다. (구매자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "계약 신청 성공"),
            @ApiResponse(responseCode = "400", description = "계약 가능한 상태의 매물이 아닙니다 / 본인 매물에는 신청할 수 없습니다"),
            @ApiResponse(responseCode = "403", description = "구매자만 접근할 수 있습니다"),
            @ApiResponse(responseCode = "404", description = "매물을 찾을 수 없습니다"),
            @ApiResponse(responseCode = "409", description = "이미 해당 매물에 계약 신청이 존재합니다")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/listings/{listingId}/contracts")
    ResponseEntity<BaseResponse<ContractApplyResDto>> createContract(
            @PathVariable Long listingId,
            @Valid @RequestBody CreateContractReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "내 매물 계약 목록 조회", description = "판매자가 자신의 매물에 대한 계약 신청 목록을 조회합니다. (판매자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "계약 목록 조회 성공"),
            @ApiResponse(responseCode = "403", description = "해당 매물의 소유자가 아닙니다"),
            @ApiResponse(responseCode = "404", description = "매물을 찾을 수 없습니다")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/listings/{listingId}/contracts")
    ResponseEntity<BaseResponse<List<SellerContractSummaryResDto>>> getSellerContracts(
            @PathVariable Long listingId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "계약 상태 변경", description = "판매자가 계약 상태를 변경합니다. (REQUESTED→APPROVED/REJECTED, APPROVED→COMPLETED)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "계약 상태 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 상태 변경입니다"),
            @ApiResponse(responseCode = "403", description = "판매자만 접근할 수 있습니다"),
            @ApiResponse(responseCode = "404", description = "계약을 찾을 수 없습니다"),
            @ApiResponse(responseCode = "409", description = "이미 승인된 계약이 존재합니다")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/contracts/{contractId}/status")
    ResponseEntity<BaseResponse<ContractStatusResDto>> updateContractStatus(
            @PathVariable Long contractId,
            @Valid @RequestBody UpdateContractStatusReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "계약 상태 조회", description = "구매자가 자신의 계약 상태를 조회합니다. (구매자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "계약 상태 조회 성공"),
            @ApiResponse(responseCode = "403", description = "해당 계약의 구매자가 아닙니다"),
            @ApiResponse(responseCode = "404", description = "계약을 찾을 수 없습니다")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/contracts/{contractId}/status")
    ResponseEntity<BaseResponse<ContractStatusResDto>> getContractStatus(
            @PathVariable Long contractId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "체크리스트 조회", description = "구매자가 자신의 계약 체크리스트를 조회합니다. (구매자 전용)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "체크리스트 조회 성공"),
            @ApiResponse(responseCode = "403", description = "해당 계약의 구매자가 아닙니다"),
            @ApiResponse(responseCode = "404", description = "계약을 찾을 수 없습니다")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/contracts/{contractId}/checklists")
    ResponseEntity<BaseResponse<ChecklistResDto>> getChecklist(
            @PathVariable Long contractId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "체크리스트 수정", description = "구매자가 체크리스트 항목의 체크 상태를 수정합니다. (구매자 전용, REJECTED/CANCELLED 상태 수정 불가)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "체크리스트 수정 성공"),
            @ApiResponse(responseCode = "400", description = "수정할 수 없는 상태의 계약입니다"),
            @ApiResponse(responseCode = "403", description = "해당 계약의 구매자가 아닙니다"),
            @ApiResponse(responseCode = "404", description = "계약을 찾을 수 없습니다 / 체크리스트 항목을 찾을 수 없습니다")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/contracts/{contractId}/checklists")
    ResponseEntity<BaseResponse<ChecklistResDto>> updateChecklist(
            @PathVariable Long contractId,
            @Valid @RequestBody UpdateChecklistReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );

    @Operation(summary = "계약서/AI 해석/유의사항 조회", description = "구매자가 계약서 본문, AI 해석, 유의사항을 조회합니다. (APPROVED/COMPLETED 상태만 가능)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "계약서 문서 조회 성공"),
            @ApiResponse(responseCode = "400", description = "계약서 내용이 아직 준비되지 않았습니다"),
            @ApiResponse(responseCode = "403", description = "해당 계약의 구매자가 아닙니다"),
            @ApiResponse(responseCode = "404", description = "계약을 찾을 수 없습니다")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/contracts/{contractId}/document")
    ResponseEntity<BaseResponse<ContractDocumentResDto>> getContractDocument(
            @PathVariable Long contractId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    );
}
