package com.likelion.besession.domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Role {

    @Schema(description = "초보")
    BEGINNER,

    @Schema(description = "중급")
    INTERMEDIATE,

    @Schema(description = "고급")
    ADVANCED
}
