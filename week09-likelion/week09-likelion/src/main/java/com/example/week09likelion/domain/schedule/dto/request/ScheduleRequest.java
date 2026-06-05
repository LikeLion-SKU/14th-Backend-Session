package com.example.week09likelion.domain.schedule.dto.request;

import com.example.week09likelion.domain.schedule.entity.ScheduleStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleRequest {

    @NotNull(message = "계약 단계는 필수입니다.")
    private ScheduleStage stage;

    @NotBlank(message = "일정 제목은 필수입니다.")
    private String title;

    private LocalDate dueDate;

    private Boolean isCompleted;
}