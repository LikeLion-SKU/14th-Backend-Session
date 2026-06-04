package com.likelion.besession.domain.checklist.controller;

import com.likelion.besession.domain.checklist.dto.response.CheckListResponse;
import com.likelion.besession.domain.checklist.service.CheckListService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "CheckList", description = "체크리스트 관련 API")
public class CheckListController {

    private final CheckListService checkListService;

    @Operation(summary = "체크리스트 조회", description = "현재 계약 단계에 해당하는 체크리스트 목록을 조회하는 API")
    @GetMapping("/contracts/{contract-id}/checklists")
    public ResponseEntity<BaseResponse<List<CheckListResponse>>> allCheckList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("contract-id") Long contractId) {
        List<CheckListResponse> response = checkListService.checkListLook(userDetails.getUserId(), contractId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "체크리스트 조회에 성공했습니다.", response));
    }

    @Operation(summary = "체크리스트 완료", description = "체크리스트 항목을 완료 처리하는 API")
    @PutMapping("/checklists/{checkList-id}/complete")
    public ResponseEntity<BaseResponse<CheckListResponse>> completeCheckList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("checkList-id") Long checkListId) {
        CheckListResponse response = checkListService.completeCheckList(userDetails.getUserId(), checkListId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "체크리스트가 완료되었습니다.", response));
    }

    @Operation(summary = "체크리스트 완료 취소", description = "체크리스트 항목의 완료를 취소하는 API")
    @PutMapping("/checklists/{checkList-id}/incomplete")
    public ResponseEntity<BaseResponse<CheckListResponse>> incompleteCheckList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("checkList-id") Long checkListId) {
        CheckListResponse response = checkListService.incompleteCheckList(userDetails.getUserId(), checkListId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "체크리스트 완료가 취소되었습니다.", response));
    }

    @Operation(summary = "체크리스트 삭제", description = "체크리스트 항목을 삭제하는 API")
    @DeleteMapping("/checklists/{checkList-id}")
    public ResponseEntity<BaseResponse<Void>> deleteCheckList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("checkList-id") Long checkListId) {
        checkListService.deleteCheckList(userDetails.getUserId(), checkListId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "체크리스트가 삭제되었습니다.", null));
    }
}
