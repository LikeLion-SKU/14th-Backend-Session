package com.likelion.besession.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "UpdateUserRequest: 프로필 수정 요청 DTO")
public class UpdateUserRequest {

    @NotBlank(message = "사용자 이름 항목은 필수입니다.")
    @Schema(description = "이름", example = "홍길")
    private String name;

    @Schema(description = "프로필 사진", example = "https://example.com/images/new-profile.jpg")
    private String image;
}
