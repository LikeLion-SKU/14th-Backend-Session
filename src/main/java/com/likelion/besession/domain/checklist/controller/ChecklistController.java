package com.likelion.besession.domain.checklist.controller;

import com.likelion.besession.domain.checklist.dto.ChecklistResponse;
import com.likelion.besession.domain.checklist.dto.ChecklistUpdateRequest;
import com.likelion.besession.domain.checklist.service.ChecklistService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Checklist", description = "체크리스트 관련 API")
public class ChecklistController {

    private final ChecklistService checklistService;

    @GetMapping("/api/contracts/me/checklists")
    @Operation(summary = "내 계약 체크리스트 조회", description = "로그인한 사용자의 계약에 연결된 체크리스트 목록을 조회합니다.")
    public BaseResponse<List<ChecklistResponse>> getMyChecklists(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return BaseResponse.success(checklistService.getMyChecklists(userDetails.getUser()));
    }

    @PatchMapping("/api/checklists/{checklistId}")
    @Operation(summary = "체크리스트 체크 여부 수정", description = "체크리스트 항목의 완료 여부를 true 또는 false로 수정합니다.")
    public BaseResponse<Void> updateChecklist(
            @PathVariable Long checklistId,
            @RequestBody ChecklistUpdateRequest request
    ) {
        checklistService.updateChecklist(checklistId, request);
        return BaseResponse.success("체크리스트 수정 성공", null);
    }
}