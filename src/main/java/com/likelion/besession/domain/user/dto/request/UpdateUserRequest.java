package com.likelion.besession.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(title = "UpdateUserRequest: 내 정보 수정 요청 DTO")
public record UpdateUserRequest(

    @NotBlank(message = "이름은 필수입니다.")
    @Schema(description = "이름", example = "홍길동")
    String name
) {}
