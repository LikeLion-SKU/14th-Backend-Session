package com.likjelion.besession.week09_domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "로그인 응답 DTO")
public class LoginResponse {

    @Schema(description = "생성된 accessToken")
    private String accessToken;

    @Schema(description = "생성된 refreshToken(재발급용)")
    private String refreshToken;
}
