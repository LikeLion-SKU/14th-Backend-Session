package com.likelion.besession.domain.schedule.controller;

import com.likelion.besession.domain.schedule.dto.request.ScheduleCreateRequest;
import com.likelion.besession.domain.schedule.dto.response.MyScheduleResponse;
import com.likelion.besession.domain.schedule.dto.response.ScheduleResponse;
import com.likelion.besession.domain.schedule.service.ScheduleService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contracts")
@Tag(name = "Schedule", description = "계약 일정 관련 API")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "나의 현재 일정 조회", description = "계약 ID로 주소, 진행률, 계약 상태, 전체 일정 목록(phase 포함)을 조회")
    @GetMapping("/{id}/schedules")
    public ResponseEntity<BaseResponse<MyScheduleResponse>> getSchedules(@PathVariable Long id) {
        return ResponseEntity.ok(BaseResponse.success(scheduleService.getSchedules(id)));
    }

    @Operation(summary = "(AI) 계약 일정 생성", description = "계약 ID에 일정 이름과 단계를 입력받아 일정을 생성")
    @PostMapping("/{id}/schedules")
    public ResponseEntity<BaseResponse<ScheduleResponse>> createSchedule(
        @PathVariable Long id,
        @RequestBody ScheduleCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(BaseResponse.success(scheduleService.createSchedule(id, request)));
    }
}
