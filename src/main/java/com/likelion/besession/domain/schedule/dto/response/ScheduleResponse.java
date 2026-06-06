package com.likelion.besession.domain.schedule.dto.response;

import com.likelion.besession.domain.schedule.entity.ContractSchedule;
import com.likelion.besession.domain.schedule.entity.Phase;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ScheduleResponse {

    @Schema(description = "일정 ID", example = "1")
    private Long scheduleId;

    @Schema(description = "계약 ID", example = "1")
    private Long contractId;

    @Schema(description = "일정 이름", example = "계약서 검토")
    private String title;

    @Schema(description = "계약 단계", example = "BEFORE")
    private Phase phase;

    @Schema(description = "생성 날짜", example = "2024-01-01")
    private LocalDate createdAt;

    @Schema(description = "수정 날짜", example = "2024-01-02")
    private LocalDate updatedAt;

    public static ScheduleResponse from(ContractSchedule schedule) {
        return ScheduleResponse.builder()
            .scheduleId(schedule.getScheduleId())
            .contractId(schedule.getContract().getContractId())
            .title(schedule.getTitle())
            .phase(schedule.getPhase())
            .createdAt(schedule.getCreatedAt())
            .updatedAt(schedule.getUpdatedAt())
            .build();
    }
}
