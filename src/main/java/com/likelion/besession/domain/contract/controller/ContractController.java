package com.likelion.besession.domain.contract.controller;

import com.likelion.besession.domain.contract.dto.request.ChecklistStatusRequest;
import com.likelion.besession.domain.contract.dto.request.ContractCreateRequest;
import com.likelion.besession.domain.contract.dto.response.ChecklistResponse;
import com.likelion.besession.domain.contract.dto.response.ContractResponse;
import com.likelion.besession.domain.contract.entity.ContractPhase;
import com.likelion.besession.domain.contract.service.ContractService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contracts")
@Tag(name = "Contract", description = "계약 관련 API")
public class ContractController {

    private final ContractService contractService;

    @Operation(summary = "계약 생성", description = "사용자 ID, 계약서 내용, 주소를 입력받아 새 계약 생성")
    @PostMapping
    public ResponseEntity<BaseResponse<ContractResponse>> createContract(@RequestBody ContractCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse.success(contractService.createContract(request)));
    }

    @Operation(summary = "계약 상세 조회", description = "계약 ID로 계약 전체 정보를 조회")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ContractResponse>> getContract(@PathVariable Long id) {
        return ResponseEntity.ok(BaseResponse.success(contractService.getContract(id)));
    }

    @Operation(summary = "체크리스트 목록 조회", description = "phase(계약전/계약중/계약후)로 필터링하거나, 파라미터 없이 전체 조회")
    @GetMapping("/{id}/checklists")
    public ResponseEntity<BaseResponse<List<ChecklistResponse>>> getChecklists(
        @PathVariable Long id,
        @RequestParam(required = false) ContractPhase phase) {
        return ResponseEntity.ok(BaseResponse.success(contractService.getChecklists(id, phase)));
    }

    @Operation(summary = "체크 상태 변경", description = "체크리스트 항목의 체크 여부 변경")
    @PatchMapping("/{id}/checklists/{checklistId}")
    public ResponseEntity<BaseResponse<ChecklistResponse>> updateChecklistStatus(
        @PathVariable Long id,
        @PathVariable Long checklistId,
        @RequestBody ChecklistStatusRequest request) {
        return ResponseEntity.ok(BaseResponse.success(contractService.updateChecklistStatus(id, checklistId, request)));
    }
}
