package com.likelion.besession.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Schema(title = "UpdatePasswordRequest: 비밀번호 변경 요청 DTO")
public record UpdatePasswordRequest(

    @NotBlank(message = "현재 비밀번호는 필수입니다.")
    @Schema(description = "현재 비밀번호", example = "password123")
    String currentPassword,

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    @Schema(description = "새 비밀번호", example = "newPassword456")
    String newPassword
) {}
