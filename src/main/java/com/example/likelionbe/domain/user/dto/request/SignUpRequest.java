package com.example.likelionbe.domain.user.dto.request;

import com.example.likelionbe.domain.user.entity.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(title = "SignupRequest: 회원가입 요청 DTO")
public record SignUpRequest(
        @NotBlank(message = "사용자 이메일 항목은 필수입니다.")
        @Schema(description = "이메일", example = "example@naver.com")
        String email,

        @NotBlank(message = "사용자 비밀번호 항목은 필수입니다.")
        @Schema(description = "비밀번호", example = "password123")
        String password,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "사용자 종류", example = "BUYER")
        UserType userType
) {
}
