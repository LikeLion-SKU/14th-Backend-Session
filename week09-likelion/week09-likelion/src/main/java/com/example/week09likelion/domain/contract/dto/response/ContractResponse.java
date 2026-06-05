package com.example.week09likelion.domain.contract.dto.response;

import com.example.week09likelion.domain.contract.entity.Contract;
import com.example.week09likelion.domain.contract.entity.ContractStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContractResponse {

    private Long id;
    private String title;
    private String address;
    private ContractStatus status;
    private Integer progress;
    private LocalDateTime createdAt;

    // 계약 응답 생성
    public static ContractResponse from(Contract contract) {
        return ContractResponse.builder()
                .id(contract.getId())
                .title(contract.getTitle())
                .address(contract.getAddress())
                .status(contract.getStatus())
                .progress(contract.getProgress())
                .createdAt(contract.getCreatedAt())
                .build();
    }
}