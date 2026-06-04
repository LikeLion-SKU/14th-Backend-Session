package com.likelion.besession.domain.schedule.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Schema(title = "CreateScheduleRequest: 일정 생성 요청 DTO")
public class CreateScheduleRequest {

    @NotBlank(message = "단계(stage)는 필수입니다.")
    @Schema(description = "계약 단계", example = "계약 체결")
    private String stage;

    @NotBlank(message = "일정 제목은 필수입니다.")
    @Schema(description = "일정 제목", example = "부동산 계약서 서명")
    private String title;

    @NotNull(message = "일정 날짜는 필수입니다.")
    @Schema(description = "일정 날짜", example = "2024-03-15")
    private LocalDate date;

    @Schema(description = "경고 알림 시각", example = "2024-03-14T09:00:00")
    private LocalDateTime warningAt;
}
