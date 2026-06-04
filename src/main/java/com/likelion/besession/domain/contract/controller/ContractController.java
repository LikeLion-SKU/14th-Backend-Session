package com.likelion.besession.domain.contract.controller;

import com.likelion.besession.domain.contract.dto.request.CreateContractRequest;
import com.likelion.besession.domain.contract.dto.request.UpdateContractRequest;
import com.likelion.besession.domain.contract.dto.response.ContractResponse;
import com.likelion.besession.domain.contract.service.ContractService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Contract", description = "부동산 계약 일정 관련 API")
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "계약 일정 생성", description = "요청으로 전달된 정보로 새로운 계약 일정을 생성하는 API")
    @PostMapping("/contracts")
    public ResponseEntity<BaseResponse<ContractResponse>> createContract(@Valid @RequestBody CreateContractRequest request) {
        ContractResponse contractResponse = contractService.createContract(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약 일정 생성에 성공했습니다.", contractResponse));
    }

    @Operation(summary = "계약 일정 전체 조회", description = "모든 계약 일정 목록을 조회하는 API")
    @GetMapping("/contracts")
    public ResponseEntity<BaseResponse<List<ContractResponse>>> getAllContracts() {
        List<ContractResponse> contractResponseList = contractService.getAllContracts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(contractResponseList));
    }

    @Operation(summary = "계약 일정 단건 조회", description = "계약 ID로 특정 계약 일정을 조회하는 API")
    @GetMapping("/contracts/{contract-id}")
    public ResponseEntity<BaseResponse<ContractResponse>> getContractById(@PathVariable("contract-id") Long contractId) {
        ContractResponse contractResponse = contractService.getContractById(contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(contractResponse));
    }

    @Operation(summary = "계약 일정 수정", description = "계약 ID와 요청으로 전달된 정보로 계약 일정을 수정하는 API")
    @PutMapping("/contracts/{contract-id}")
    public ResponseEntity<BaseResponse<ContractResponse>> updateContract(
            @PathVariable("contract-id") Long contractId, @Valid @RequestBody UpdateContractRequest request) {
        ContractResponse contractResponse = contractService.updateContract(contractId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(contractResponse));
    }

    @Operation(summary = "계약 일정 삭제", description = "계약 ID로 특정 계약 일정을 삭제하는 API")
    @DeleteMapping("/contracts/{contract-id}")
    public ResponseEntity<BaseResponse<Boolean>> deleteContract(@PathVariable("contract-id") Long contractId) {
        Boolean result = contractService.deleteContract(contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(result));
    }
}