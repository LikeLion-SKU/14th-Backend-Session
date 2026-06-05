package com.likjelion.besession.week09_domain.contract.dto.response;

import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateContractResponse {
    private Long contractId;
    private Long propertyId;
    private ContractStatus contractStatus;
    private int processRate;
    private LocalDateTime createdAt;
}
