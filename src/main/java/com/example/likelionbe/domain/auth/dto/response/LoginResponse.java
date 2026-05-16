package com.example.likelionbe.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
public record LoginResponse(
        @Schema(description = "Access Token")
        String accessToken
) {
}
