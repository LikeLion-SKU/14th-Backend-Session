package com.likjelion.besession.week09_domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "토큰 재발급 응답 DTO")
public class TokenResponse {

    @Schema(description = "재발급된 accessToken")
    private String accessToken;

}
