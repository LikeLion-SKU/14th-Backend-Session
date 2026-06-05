package com.likjelion.besession.week09_domain.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Gender {

    @Schema(description = "남자")
    Male,
    @Schema(description = "여자")
    Female
}
