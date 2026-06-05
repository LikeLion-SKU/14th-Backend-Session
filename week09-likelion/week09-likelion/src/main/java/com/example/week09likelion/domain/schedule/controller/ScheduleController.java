package com.example.week09likelion.domain.schedule.controller;

import com.example.week09likelion.domain.schedule.dto.request.ScheduleRequest;
import com.example.week09likelion.domain.schedule.dto.response.ScheduleResponse;
import com.example.week09likelion.domain.schedule.entity.ScheduleStage;
import com.example.week09likelion.domain.schedule.service.ScheduleService;
import com.example.week09likelion.domain.user.exception.UserErrorCode;
import com.example.week09likelion.global.common.BaseResponse;
import com.example.week09likelion.global.exception.CustomException;
import com.example.week09likelion.security.CustomUserDetails;
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
@Tag(name = "Schedule", description = "계약 일정 관련 API")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "계약 일정 목록 조회", description = "로그인한 사용자의 계약 일정을 단계별로 조회하는 API")
    @GetMapping("/api/contracts/me/schedules")
    public ResponseEntity<BaseResponse<List<ScheduleResponse>>> getMySchedules(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(required = false) ScheduleStage stage
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        List<ScheduleResponse> scheduleResponses = scheduleService.getMySchedules(
                customUserDetails.getUser().getId(),
                stage
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약 일정 목록 조회 성공", scheduleResponses));
    }

    @Operation(summary = "계약 일정 등록", description = "로그인한 사용자의 계약 일정을 등록하는 API")
    @PostMapping("/api/contracts/me/schedules")
    public ResponseEntity<BaseResponse<ScheduleResponse>> createMySchedule(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody ScheduleRequest request
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        ScheduleResponse scheduleResponse = scheduleService.createMySchedule(
                customUserDetails.getUser().getId(),
                request
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계약 일정 등록 성공", scheduleResponse));
    }

    @Operation(summary = "계약 일정 수정", description = "계약 일정의 제목, 날짜, 단계, 완료 여부를 수정하는 API")
    @PutMapping("/api/schedules/{scheduleId}")
    public ResponseEntity<BaseResponse<ScheduleResponse>> updateSchedule(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long scheduleId,
            @Valid @RequestBody ScheduleRequest request
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(
                customUserDetails.getUser().getId(),
                scheduleId,
                request
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약 일정 수정 성공", scheduleResponse));
    }
}