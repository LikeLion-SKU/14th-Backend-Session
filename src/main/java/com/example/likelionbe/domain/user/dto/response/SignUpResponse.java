package com.example.likelionbe.domain.user.dto.response;

import com.example.likelionbe.domain.user.entity.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title = "SignupRequest: 회원가입 응답 DTO")
public record SignUpResponse(
        @Schema(description = "생성된 사용자 ID", example = "1")
        Long userId,

        @Schema(description = "사용자 이메일", example = "example@naver.com")
        String email,

        @Schema(description = "이름", example = "홍길동")
        String name,

        @Schema(description = "사용자 종류", example = "BUYER")
        UserType userType
) {
}
