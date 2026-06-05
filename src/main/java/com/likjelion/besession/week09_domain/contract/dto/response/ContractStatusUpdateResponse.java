package com.likjelion.besession.week09_domain.contract.dto.response;

import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContractStatusUpdateResponse {
    private Long contractId;
    private ContractStatus contractStatus;
    private LocalDateTime updatedAt;
}
