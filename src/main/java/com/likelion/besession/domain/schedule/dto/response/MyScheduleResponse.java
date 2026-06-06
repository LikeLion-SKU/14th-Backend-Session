package com.likelion.besession.domain.schedule.dto.response;

import com.likelion.besession.domain.contract.entity.Contract;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyScheduleResponse {

    @Schema(description = "계약 주소", example = "서울시 강남구 테헤란로 123")
    private String address;

    @Schema(description = "진행률", example = "30")
    private int progressRate;

    @Schema(description = "계약 상태", example = "계약중")
    private String status;

    @Schema(description = "일정 목록")
    private List<ScheduleResponse> schedules;

    public static MyScheduleResponse of(Contract contract, List<ScheduleResponse> schedules) {
        return MyScheduleResponse.builder()
            .address(contract.getAddress())
            .progressRate(contract.getProgressRate())
            .status(contract.getStatus())
            .schedules(schedules)
            .build();
    }
}
