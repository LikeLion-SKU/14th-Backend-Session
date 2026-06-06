package com.likelion.besession.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(title = "UserResponse: 내 정보 응답 DTO")
public record UserResponse(

    @Schema(description = "이메일", example = "test@example.com")
    String email,

    @Schema(description = "이름", example = "홍길동")
    String name,

    @Schema(description = "유저 레벨", example = "1")
    Integer userLevel,

    @Schema(description = "현재 진행 단계", example = "BEFORE")
    String currentStage
) {}
