package com.likelion.besession.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 권한")
public enum Role {

    @Schema(description = "사용자")
    USER,

    @Schema(description = "관리자")
    ADMIN
}
