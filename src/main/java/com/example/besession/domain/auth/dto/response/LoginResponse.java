package com.example.besession.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "LoginResponse: 로그인 응답")
public class LoginResponse {
    @Schema(description = "Acess Token")
    private String accessToken;
}
