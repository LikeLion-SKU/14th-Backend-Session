package com.likelion.besession.domain.contract.dto;

import com.likelion.besession.domain.contract.entity.Contract;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractResponse {

    private Long id;
    private String name;
    private String address;
    private String contractSource;
    private String contractStep;
    private Integer progressRate;

    public static ContractResponse from(Contract contract) {
        return ContractResponse.builder()
                .id(contract.getId())
                .name(contract.getName())
                .address(contract.getAddress())
                .contractSource(contract.getContractSource())
                .contractStep(contract.getContractStep())
                .progressRate(contract.getProgressRate())
                .build();
    }
}