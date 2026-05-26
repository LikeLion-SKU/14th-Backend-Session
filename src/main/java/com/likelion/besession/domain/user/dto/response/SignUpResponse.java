package com.likelion.besession.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title = "SignUpResponse: 회원가입 응답 DTO")
public record SignUpResponse(

    @Schema(description = "생성된 사용자 ID", example = "1")
    Long userId,

    @Schema(description = "이메일", example = "test@example.com")
    String email,

    @Schema(description = "이름", example = "홍길동")
    String name
) {}

// 이전 코드 (class 방식)
// @Getter
// @Builder
// @Schema(title = "SignUpResponse: 회원가입 응답 DTO")
// public class SignUpResponse {
//
//     @Schema(description = "생성된 사용자 ID", example = "1")
//     private Long userId;
//
//     @Schema(description = "이메일", example = "test@example.com")
//     private String email;
//
//     @Schema(description = "이름", example = "홍길동")
//     private String name;
// }
