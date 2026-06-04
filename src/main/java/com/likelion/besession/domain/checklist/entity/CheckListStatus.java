package com.likelion.besession.domain.checklist.entity;

import io.swagger.v3.oas.annotations.media.Schema;

public enum CheckListStatus {

    @Schema(description = "완료 전")
    INCOMPLETE,

    @Schema(description = "완료")
    COMPLETE
}
