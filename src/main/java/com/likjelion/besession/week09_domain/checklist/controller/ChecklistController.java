package com.likjelion.besession.week09_domain.checklist.controller;

import com.likjelion.besession.global.common.BaseResponse;
import com.likjelion.besession.week09_domain.checklist.dto.request.UpdateChecklistRequest;
import com.likjelion.besession.week09_domain.checklist.dto.response.ChecklistItemResponse;
import com.likjelion.besession.week09_domain.checklist.dto.response.ChecklistUpdateResponse;
import com.likjelion.besession.week09_domain.checklist.service.ChecklistService;
import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Checklist", description = "체크리스트 API")
@RestController
@RequiredArgsConstructor
public class ChecklistController {

    private final ChecklistService checklistService;

    @Operation(summary = "계약별 체크리스트 조회", description = "특정 계약에 연결된 체크리스트 목록을 조회합니다. status 값을 전달하면 계약 전, 계약 중, 계약 후 단계별 체크리스트만 필터링할 수 있습니다.")
    @GetMapping("/api/contracts/{contractId}/checklists")
    public ResponseEntity<BaseResponse<List<ChecklistItemResponse>>> getChecklists(
            @PathVariable Long contractId,
            @Parameter(description = "필터링할 계약 상태 (선택)")
            @RequestParam(required = false) ContractStatus status) {
        return ResponseEntity.ok(BaseResponse.success(200, "체크리스트 조회 성공", checklistService.getChecklists(contractId, status)));
    }

    @Operation(summary = "체크리스트 체크/해제", description = "계약 체크리스트 항목의 체크 여부를 변경합니다. 변경 후 해당 계약의 진행률을 다시 계산하여 반환합니다.")
    @PatchMapping("/api/contract-checklists/{contractChecklistId}")
    public ResponseEntity<BaseResponse<ChecklistUpdateResponse>> updateChecked(
            @PathVariable Long contractChecklistId,
            @RequestBody UpdateChecklistRequest request) {
        return ResponseEntity.ok(BaseResponse.success(200, "체크리스트 업데이트 성공", checklistService.updateChecked(contractChecklistId, request)));
    }
}
