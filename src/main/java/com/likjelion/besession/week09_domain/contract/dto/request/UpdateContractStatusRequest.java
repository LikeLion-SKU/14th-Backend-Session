package com.likjelion.besession.week09_domain.contract.dto.request;

import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateContractStatusRequest {
    private ContractStatus contractStatus;
}
