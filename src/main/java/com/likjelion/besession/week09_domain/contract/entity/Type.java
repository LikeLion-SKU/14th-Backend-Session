package com.likjelion.besession.week09_domain.contract.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Type {

    @Schema(description = "월세")
    MONTHLY_RENT,
    @Schema(description = "전세")
    JEONSE,
    @Schema(description = "매매")
    SALE
}
