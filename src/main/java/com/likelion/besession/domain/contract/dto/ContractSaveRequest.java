package com.likelion.besession.domain.contract.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "계약 저장 요청 DTO")
public class ContractSaveRequest {

    @Schema(description = "계약 이름", example = "서경로 전세 계약")
    private String name;

    @Schema(description = "계약 주소", example = "서울특별시 성북구 서경로 124")
    private String address;

    @Schema(description = "계약서 원문 내용", example = "임차인이 2개월 이상 차임을 연체할 경우 임대인은 계약을 해지할 수 있다.")
    private String contractSource;

    @Schema(description = "계약 진행 단계", example = "BEFORE_CONTRACT")
    private String contractStep;

    @Schema(description = "계약 진행률", example = "70")
    private Integer progressRate;
}