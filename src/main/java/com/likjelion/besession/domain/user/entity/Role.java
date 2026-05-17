package com.likjelion.besession.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Role {

    @Schema(description = "사용자")
    USER,

    @Schema(description = "사용자")
    ADMIN
}
