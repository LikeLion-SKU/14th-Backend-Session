package com.likjelion.besession.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LoginResponse: 로그인 응답 DTO")
public class LoginResponse {

    @Schema(description = "생성된 사용자 ID", example = "1")
    private String accessToken;
}
