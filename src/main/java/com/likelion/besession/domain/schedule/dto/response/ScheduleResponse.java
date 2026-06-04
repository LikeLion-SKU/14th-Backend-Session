package com.likelion.besession.domain.schedule.dto.response;

import com.likelion.besession.domain.schedule.entity.Schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(title = "ScheduleResponse: 일정 응답 DTO")
public class ScheduleResponse {

    @Schema(description = "일정 ID", example = "1")
    private Long id;

    @Schema(description = "계약 단계", example = "계약 체결")
    private String stage;

    @Schema(description = "일정 제목", example = "부동산 계약서 서명")
    private String title;

    @Schema(description = "일정 날짜", example = "2024-03-15")
    private LocalDate date;

    @Schema(description = "경고 알림 시각")
    private LocalDateTime warningAt;

    @Schema(description = "체크리스트 목록")
    private List<CheckListResponse> checkLists;

    public static ScheduleResponse from(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .stage(schedule.getStage())
                .title(schedule.getTitle())
                .date(schedule.getDate())
                .warningAt(schedule.getWarningAt())
                .checkLists(schedule.getCheckLists().stream()
                        .map(CheckListResponse::from)
                        .toList())
                .build();
    }
}
