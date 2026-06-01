package com.likelion.besession.domain.user.dto.response;

import com.likelion.besession.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(title = "UpdateUserResponse: 회원 정보 수정 응답 DTO")
public class UpdateUserResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "이메일", example = "test@example.com")
    private String email;

    @Schema(description = "수정된 이름", example = "홍길")
    private String name;

    @Schema(description = "레벨", example = "초보")
    private Role role;

    @Schema(description = "수정된 프로필 사진 URL", example = "https://example.com/images/new-profile.jpg")
    private String image;

    @Schema(description = "수정 시각", example = "2026-05-30T10:00:00")
    private LocalDateTime updatedAt;
}
