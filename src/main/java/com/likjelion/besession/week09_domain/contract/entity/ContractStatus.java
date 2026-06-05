package com.likjelion.besession.week09_domain.contract.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ContractStatus {
    @Schema(description = "계약 전")
    BEFORE_CONTRACT,
    @Schema(description = "계약 중")
    IN_CONTRACT,
    @Schema(description = "계약 후")
    AFTER_CONTRACT

}
