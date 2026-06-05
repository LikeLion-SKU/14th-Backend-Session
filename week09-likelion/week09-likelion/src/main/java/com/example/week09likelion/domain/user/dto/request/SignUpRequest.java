package com.example.week09likelion.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title="SignUpRequest: 회원가입 요청 DTO")
public class SignUpRequest {
    @NotBlank(message = "사용자 이메일 항목은 필수입니다.")
    @Schema(description = "이메일", example = "test@example.com")
    private String email;

    @NotBlank(message = "사용자 비밀번호 항목은 필수입니다.")
    @Schema(description = "비밀번호", example = "password1234")
    private String password;

    @NotBlank(message = "사용자 이름 항목은 필수입니다.")
    @Schema(description = "이름", example = "홍길동")
    private String name;




}
