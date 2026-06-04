package com.likelion.besession.domain.schedule.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Schema(title = "ScheduleTimelineResponse: 일정 타임라인 응답 DTO")
public class ScheduleTimelineResponse {

    @Schema(description = "일정 ID", example = "1")
    private Long scheduleId;

    @Schema(description = "일정 제목", example = "부동산 계약서 서명")
    private String title;

    @Schema(description = "일정 날짜")
    private LocalDate date;

    @Schema(description = "계약 단계")
    private String stage;

    @Schema(description = "체크리스트 전체 개수", example = "5")
    private int totalCheckLists;

    @Schema(description = "완료된 체크리스트 개수", example = "3")
    private int completedCheckLists;

    @Schema(description = "체크리스트 목록")
    private List<CheckListResponse> checkLists;

    public static ScheduleTimelineResponse from(com.likelion.besession.domain.schedule.entity.Schedule schedule) {
        List<CheckListResponse> checkListResponses = schedule.getCheckLists().stream()
                .map(CheckListResponse::from)
                .toList();

        // 완료된 체크리스트 개수 계산
        long completed = schedule.getCheckLists().stream()
                .filter(c -> c.isChecked())
                .count();

        return ScheduleTimelineResponse.builder()
                .scheduleId(schedule.getId())
                .title(schedule.getTitle())
                .date(schedule.getDate())
                .stage(schedule.getStage())
                .totalCheckLists(checkListResponses.size())
                .completedCheckLists((int) completed)
                .checkLists(checkListResponses)
                .build();
    }
}
