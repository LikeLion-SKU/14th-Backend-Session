package com.likelion.besession.domain.schedule.controller;

import com.likelion.besession.domain.schedule.dto.request.CreateScheduleRequest;
import com.likelion.besession.domain.schedule.dto.response.CheckListResponse;
import com.likelion.besession.domain.schedule.dto.response.ScheduleResponse;
import com.likelion.besession.domain.schedule.dto.response.ScheduleTimelineResponse;
import com.likelion.besession.domain.schedule.service.ScheduleService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.config.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/schedules")
@Tag(name = "Schedule", description = "일정 관련 API")
@SecurityRequirement(name = "bearerAuth")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "일정 생성", description = "새 일정을 등록하는 API")
    @PostMapping
    public ResponseEntity<BaseResponse<ScheduleResponse>> createSchedule(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CreateScheduleRequest request) {
        ScheduleResponse response = scheduleService.createSchedule(userDetails.getUser().getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "일정이 등록되었습니다.", response));
    }

    @Operation(summary = "내 일정 목록 조회", description = "로그인한 사용자의 일정 목록을 날짜 순으로 조회하는 API")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<List<ScheduleResponse>>> getMySchedules(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ScheduleResponse> response = scheduleService.getMySchedules(userDetails.getUser().getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "일정 타임라인 조회", description = "특정 일정의 타임라인 및 체크리스트 진행 현황을 조회하는 API")
    @GetMapping("/{scheduleId}/timeline")
    public ResponseEntity<BaseResponse<ScheduleTimelineResponse>> getTimeline(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long scheduleId) {
        ScheduleTimelineResponse response = scheduleService.getTimeline(userDetails.getUser().getId(), scheduleId);
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "체크리스트 상태 변경", description = "체크리스트 항목의 완료 여부를 토글하는 API")
    @PatchMapping("/{scheduleId}/checklists/{checkListId}")
    public ResponseEntity<BaseResponse<CheckListResponse>> toggleCheckList(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long scheduleId,
            @PathVariable Long checkListId) {
        CheckListResponse response = scheduleService.toggleCheckList(
                userDetails.getUser().getId(), scheduleId, checkListId);
        return ResponseEntity.ok(BaseResponse.success("체크리스트 상태가 변경되었습니다.", response));
    }
}
