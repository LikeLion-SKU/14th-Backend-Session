package com.likelion.besession.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(title = "UpdateProfileRequest: 프로필 수정 요청 DTO")
public class UpdateProfileRequest {

    @Schema(description = "변경할 이름", example = "홍길동")
    private String name;

    @Schema(description = "프로필 이미지 URL", example = "https://example.com/img.png")
    private String profileImg;
}
