package com.likelion.besession.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LoginResponse: 로그인 응답 DTO")
public class LoginResponse {

    @Schema(description = "Access Token")
    private String accessToken; // 로그인 성공 시 발급되는 토큰 추가
}
