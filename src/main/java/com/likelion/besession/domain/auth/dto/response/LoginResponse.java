package com.likelion.besession.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title = "LoginResponse: 로그인 응답 DTO")
public record LoginResponse(

    @Schema(description = "Access Token")
    String accessToken
) {}

// 이전 코드 (class 방식)
// @Getter
// @Builder
// @Schema(title = "LoginResponse: 로그인 응답 DTO")
// public class LoginResponse {
//
//     @Schema(description = "Access Token")
//     private String accessToken;
// }
