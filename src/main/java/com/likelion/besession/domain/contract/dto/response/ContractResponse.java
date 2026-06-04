package com.likelion.besession.domain.contract.dto.response;

import com.likelion.besession.domain.contract.entity.Stage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractResponse {
    private Long contractId;
    private Long userId;
    private String address;
    private Stage stage;
    private int progressRate;
}