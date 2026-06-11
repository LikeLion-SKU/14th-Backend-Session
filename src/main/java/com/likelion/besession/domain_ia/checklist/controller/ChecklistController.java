package com.likelion.besession.domain_ia.checklist.controller;

import com.likelion.besession.domain_ia.checklist.dto.request.ChecklistUpdateRequest;
import com.likelion.besession.domain_ia.checklist.dto.response.ChecklistDetailResponse;
import com.likelion.besession.domain_ia.checklist.service.ChecklistService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Checklist", description = "체크리스트 관련 API")
@RestController
@RequestMapping("/api")
public class ChecklistController {

    private final ChecklistService checklistService;

    public ChecklistController(ChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @GetMapping("/api/contracts/{contractId}/checklists")
    @Operation(summary = "계약 체크리스트 확인", description = "계약 체크리스트 체크 여부 확인하는 API")
    public ResponseEntity<BaseResponse<List<ChecklistDetailResponse>>> getChecklistStatus(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long contractId){

        List<ChecklistDetailResponse> checklistDetailResponseList = checklistService.getChecklistDetails(contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(checklistDetailResponseList));
    }

    @PatchMapping("/api/contract-checklist-status/{contractChecklistStatusId}")
    @Operation(summary = "계약 체크리스트 체크 여부 업데이트", description = "체크리스트 체크 해제 수정하는 API")
    public ResponseEntity<BaseResponse<ChecklistDetailResponse>> updateChecklistStatus(
            @PathVariable Long contractChecklistStatusId,
            @RequestBody ChecklistUpdateRequest checklistUpdateRequest){
        ChecklistDetailResponse checklistDetailResponse = checklistService.updateChecklist(contractChecklistStatusId, checklistUpdateRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(checklistDetailResponse));
    }
}
