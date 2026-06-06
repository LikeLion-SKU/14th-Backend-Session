package com.likelion.besession.domain.schedule.dto.request;

import com.likelion.besession.domain.schedule.entity.Phase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleCreateRequest {

    @Schema(description = "일정 이름", example = "계약서 검토")
    private String title;

    @Schema(description = "계약 단계 (BEFORE / DURING / AFTER)", example = "BEFORE")
    private Phase phase;
}
