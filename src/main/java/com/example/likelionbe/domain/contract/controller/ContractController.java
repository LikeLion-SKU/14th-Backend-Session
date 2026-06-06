package com.example.likelionbe.domain.contract.controller;

import com.example.likelionbe.domain.contract.dto.*;
import com.example.likelionbe.domain.contract.service.ContractService;
import com.example.likelionbe.global.common.BaseResponse;
import com.example.likelionbe.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContractController implements ContractControllerDocs {

    private final ContractService contractService;

    @Override
    public ResponseEntity<BaseResponse<ContractApplyResDto>> createContract(
            @PathVariable Long listingId,
            @Valid @RequestBody CreateContractReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long buyerId = userDetails.getUser().getId();
        ContractApplyResDto result = contractService.createContract(listingId, reqDto, buyerId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약 신청에 성공했습니다.", result));
    }

    @Override
    public ResponseEntity<BaseResponse<List<SellerContractSummaryResDto>>> getSellerContracts(
            @PathVariable Long listingId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long sellerId = userDetails.getUser().getId();
        List<SellerContractSummaryResDto> result = contractService.getSellerContracts(listingId, sellerId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Override
    public ResponseEntity<BaseResponse<ContractStatusResDto>> updateContractStatus(
            @PathVariable Long contractId,
            @Valid @RequestBody UpdateContractStatusReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long sellerId = userDetails.getUser().getId();
        ContractStatusResDto result = contractService.updateContractStatus(contractId, reqDto, sellerId);
        return ResponseEntity.ok(BaseResponse.success("계약 상태가 변경되었습니다.", result));
    }

    @Override
    public ResponseEntity<BaseResponse<ContractStatusResDto>> getContractStatus(
            @PathVariable Long contractId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long buyerId = userDetails.getUser().getId();
        ContractStatusResDto result = contractService.getContractStatus(contractId, buyerId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Override
    public ResponseEntity<BaseResponse<ChecklistResDto>> getChecklist(
            @PathVariable Long contractId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long buyerId = userDetails.getUser().getId();
        ChecklistResDto result = contractService.getChecklist(contractId, buyerId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Override
    public ResponseEntity<BaseResponse<ChecklistResDto>> updateChecklist(
            @PathVariable Long contractId,
            @Valid @RequestBody UpdateChecklistReqDto reqDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long buyerId = userDetails.getUser().getId();
        ChecklistResDto result = contractService.updateChecklist(contractId, reqDto, buyerId);
        return ResponseEntity.ok(BaseResponse.success("체크리스트가 업데이트되었습니다.", result));
    }

    @Override
    public ResponseEntity<BaseResponse<ContractDocumentResDto>> getContractDocument(
            @PathVariable Long contractId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long buyerId = userDetails.getUser().getId();
        ContractDocumentResDto result = contractService.getContractDocument(contractId, buyerId);
        return ResponseEntity.ok(BaseResponse.success(result));
    }
}
