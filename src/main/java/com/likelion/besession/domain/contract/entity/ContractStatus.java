package com.likelion.besession.domain.contract.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum ContractStatus {

    @Schema(description = "계약 전")
    BEFORE,

    @Schema(description = "계약 중")
    DURING,

    @Schema(description = "계약 후")
    AFTER
}
