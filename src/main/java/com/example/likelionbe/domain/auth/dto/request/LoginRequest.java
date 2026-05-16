package com.example.likelionbe.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "LoginRequest: 로그인 요청 DTO")
public record LoginRequest(
        @NotBlank(message = "사용자 이메일 항목은 필수입니다.")
        @Schema(description = "이메일", example = "example@naver.com")
        String email,

        @NotBlank(message = "사용자 비밀번호 항목은 필수입니다.")
        @Schema(description = "비밀번호", example = "password123")
        String password
) {
}
