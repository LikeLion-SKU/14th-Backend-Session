package com.likelion.besession.domain.timeline.controller;

import com.likelion.besession.domain.timeline.dto.request.CreateTimelineRequest;
import com.likelion.besession.domain.timeline.dto.request.UpdateTimelineRequest;
import com.likelion.besession.domain.timeline.dto.response.TimelineResponse;
import com.likelion.besession.domain.timeline.service.TimelineService;
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
@Tag(name = "Timeline", description = "타임라인 관련 API")
public class TimelineController {

    private final TimelineService timelineService;

    @Operation(summary = "타임라인 조회", description = "단계별 타임라인 목록 조회")
    @GetMapping("/api/contracts/{contractId}/timelines")
    public ResponseEntity<BaseResponse<List<TimelineResponse>>> getTimelines(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId,
            @RequestParam String stage) {
        List<TimelineResponse> response = timelineService.getTimelines(
                userDetails.getUser().getId(), contractId, stage);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("타임라인 조회에 성공했습니다.", response));
    }

    @Operation(summary = "타임라인 생성", description = "새 타임라인 항목 등록")
    @PostMapping("/api/contracts/{contractId}/timelines")
    public ResponseEntity<BaseResponse<TimelineResponse>> createTimeline(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long contractId,
            @Valid @RequestBody CreateTimelineRequest request) {
        TimelineResponse response = timelineService.createTimeline(
                userDetails.getUser().getId(), contractId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success("타임라인 생성에 성공했습니다.", response));
    }

    @Operation(summary = "타임라인 수정", description = "타임라인 항목 수정")
    @PatchMapping("/api/timelines/{timelineId}")
    public ResponseEntity<BaseResponse<TimelineResponse>> updateTimeline(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long timelineId,
            @Valid @RequestBody UpdateTimelineRequest request) {
        TimelineResponse response = timelineService.updateTimeline(
                userDetails.getUser().getId(), timelineId, request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("타임라인 수정에 성공했습니다.", response));
    }

    @Operation(summary = "타임라인 삭제", description = "타임라인 항목 삭제")
    @DeleteMapping("/api/timelines/{timelineId}")
    public ResponseEntity<BaseResponse<Void>> deleteTimeline(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long timelineId) {
        timelineService.deleteTimeline(userDetails.getUser().getId(), timelineId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("타임라인 삭제에 성공했습니다.", null));
    }
}
