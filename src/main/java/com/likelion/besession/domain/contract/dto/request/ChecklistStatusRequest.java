package com.likelion.besession.domain.contract.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChecklistStatusRequest {

    @Schema(description = "체크 여부", example = "true")
    private boolean status;
}
