package com.likelion.besession.domain.contract.controller;

import com.likelion.besession.domain.contract.dto.request.UpdateCheckListRequest;
import com.likelion.besession.domain.contract.dto.response.CheckListResponse;
import com.likelion.besession.domain.contract.entity.Stage;
import com.likelion.besession.domain.contract.service.CheckListService;
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
@Tag(name = "CheckList", description = "계약 단계별 체크리스트 API")
public class CheckListController {

    private final CheckListService checkListService;

    @Operation(summary = "단계별 체크리스트 조회", description = "특정 계약의 단계별(BEFORE, DURING, AFTER) 체크리스트를 조회합니다.")
    @GetMapping("/contracts/{contract-id}/checklists")
    public ResponseEntity<BaseResponse<List<CheckListResponse>>> getCheckLists(
            @PathVariable("contract-id") Long contractId,
            @RequestParam Stage stage) {

        List<CheckListResponse> response = checkListService.getCheckLists(contractId, stage);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(response));
    }

    @Operation(summary = "체크리스트 상태 수정", description = "특정 체크리스트 항목의 완료 여부를 변경합니다.")
    @PatchMapping("/checklists/{checklist-id}")
    public ResponseEntity<BaseResponse<CheckListResponse>> updateCheckListStatus(
            @PathVariable("checklist-id") Long checkListId,
            @Valid @RequestBody UpdateCheckListRequest request) {

        CheckListResponse response = checkListService.updateCheckListStatus(checkListId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(response));
    }
}