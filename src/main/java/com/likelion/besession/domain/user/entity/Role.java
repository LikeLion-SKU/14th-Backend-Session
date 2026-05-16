package com.likelion.besession.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema; // Swagger 라이브러리가 필요합니다.

@Schema(description = "사용자 권한")
public enum Role {

    @Schema(description = "사용자")
    USER,

    @Schema(description = "관리자")
    ADMIN
}