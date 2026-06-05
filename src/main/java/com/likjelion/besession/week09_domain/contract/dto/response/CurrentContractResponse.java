package com.likjelion.besession.week09_domain.contract.dto.response;

import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CurrentContractResponse {
    private Long contractId;
    private ContractStatus contractStatus;
    private int processRate;
    private PropertyDto property;
}
