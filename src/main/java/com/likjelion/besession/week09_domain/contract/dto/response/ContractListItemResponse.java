package com.likjelion.besession.week09_domain.contract.dto.response;

import com.likjelion.besession.week09_domain.contract.entity.ContractStatus;
import com.likjelion.besession.week09_domain.contract.entity.Type;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContractListItemResponse {
    private Long contractId;
    private ContractStatus contractStatus;
    private int processRate;
    private String address;
    private Type type;
    private LocalDateTime createdAt;
}
