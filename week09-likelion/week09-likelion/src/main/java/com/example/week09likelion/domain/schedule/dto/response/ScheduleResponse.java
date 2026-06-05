package com.example.week09likelion.domain.schedule.dto.response;

import com.example.week09likelion.domain.schedule.entity.ContractSchedule;
import com.example.week09likelion.domain.schedule.entity.ScheduleStage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ScheduleResponse {

    private Long id;
    private ScheduleStage stage;
    private String title;
    private LocalDate dueDate;
    private Boolean isCompleted;

    // 계약 일정 응답 생성
    public static ScheduleResponse from(ContractSchedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .stage(schedule.getStage())
                .title(schedule.getTitle())
                .dueDate(schedule.getDueDate())
                .isCompleted(schedule.getIsCompleted())
                .build();
    }
}