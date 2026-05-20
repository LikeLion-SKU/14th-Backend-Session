package com.example.likelionkang.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 접근 제한
@AllArgsConstructor
@Schema(description = "LoginRequest: 로그인 요청 DTO")
public class LoginRequest {

    @NotBlank(message = "이메일은 비어있을 수 없습니다.")
    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String email;

    @NotBlank(message = "비밀번호는 비어있을 수 없습니다.")
    @Schema(description = "사용자 비밀번호", example = "password1234")
    private String password;
}