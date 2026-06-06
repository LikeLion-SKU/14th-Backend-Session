package com.likelion.besession.domain.checklist.controller;

import com.likelion.besession.domain.checklist.dto.request.CreateChecklistRequest;
import com.likelion.besession.domain.checklist.dto.request.UpdateChecklistRequest;
import com.likelion.besession.domain.checklist.dto.response.ChecklistResponse;
import com.likelion.besession.domain.checklist.service.ChecklistService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Checklist", description = "체크리스트 관련 API")
public class ChecklistController {

    private final ChecklistService checklistService;

    @Operation(summary = "체크리스트 조회", description = "단계별 체크리스트 목록 조회")
    @GetMapping("/api/contracts/{contractId}/checklists")
    public ResponseEntity<BaseResponse<List<ChecklistResponse>>> getChecklists(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId,
            @RequestParam String stage) {
        List<ChecklistResponse> response = checklistService.getChecklists(
                userDetails.getUser().getId(), contractId, stage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("체크리스트 조회에 성공했습니다.", response));
    }

    @Operation(summary = "체크리스트 생성", description = "새 체크리스트 항목 등록")
    @PostMapping("/api/contracts/{contractId}/checklists")
    public ResponseEntity<BaseResponse<ChecklistResponse>> createChecklist(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId,
            @Valid @RequestBody CreateChecklistRequest request) {
        ChecklistResponse response = checklistService.createChecklist(
                userDetails.getUser().getId(), contractId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success("체크리스트 생성에 성공했습니다.", response));
    }

    @Operation(summary = "체크 토글/수정", description = "체크 여부 변경")
    @PatchMapping("/api/checklists/{taskId}")
    public ResponseEntity<BaseResponse<ChecklistResponse>> updateChecklist(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long taskId,
            @Valid @RequestBody UpdateChecklistRequest request) {
        ChecklistResponse response = checklistService.updateChecklist(
                userDetails.getUser().getId(), taskId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("체크리스트 수정에 성공했습니다.", response));
    }

    @Operation(summary = "체크리스트 삭제", description = "체크리스트 항목 삭제")
    @DeleteMapping("/api/checklists/{taskId}")
    public ResponseEntity<BaseResponse<Void>> deleteChecklist(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long taskId) {
        checklistService.deleteChecklist(userDetails.getUser().getId(), taskId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("체크리스트 삭제에 성공했습니다.", null));
    }
}
