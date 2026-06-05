package com.example.week09likelion.domain.contract.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractReportResponse {

    private Long id;
    private String contractText;
    private String summaryText;
    private String cautionText;

    // 기본 계약서 분석 응답 생성
    public static ContractReportResponse defaultReport() {
        return ContractReportResponse.builder()
                .id(1L)
                .contractText("임차인이 2개월 이상 차임을 연체할 경우 임대인은 계약을 해지할 수 있다.")
                .summaryText("월세를 2달 이상 밀리면 계약 해지가 가능하다는 내용입니다.")
                .cautionText("연체 기준과 납부일을 계약서에 명확히 적는 것이 좋습니다.")
                .build();
    }
}