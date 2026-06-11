package com.likelion.besession.domain_ia.contract.dto.response;

import com.likelion.besession.domain_ia.contract.entity.Process;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "ContractDetailResponse : 계약서 상세 응답 DTO")
public class ContractDetailResponse {

    @Schema(description = "계약 ID")
    private Long contractId;

    @Schema(description = "부동산 주소")
    private String address;

    @Schema(description = "유저 ID")
    private Long userId;

    @Schema(description = "계약서 이미지 URL")
    private String contractImageURL;

    @Schema(description = "현재 진행 상황")
    private Process currentProcess;

    @Schema(description = "계약 종료 여부")
    private boolean isDone;
}
