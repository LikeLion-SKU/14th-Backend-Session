package com.likelion.besession.domain.user.dto.response;

import com.likelion.besession.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "UserProfileResponse: 사용자 프로필 응답 DTO")
public class UserProfileResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long id;

    @Schema(description = "이름", example = "홍길동")
    private String name;

    @Schema(description = "이메일", example = "test@example.com")
    private String email;

    @Schema(description = "프로필 이미지 URL")
    private String profileImg;

    @Schema(description = "사용자 레벨", example = "1")
    private int userLevel;

    @Schema(description = "신뢰도 점수", example = "0.0")
    private float trust;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .profileImg(user.getProfileImg())
                .userLevel(user.getUserLevel())
                .trust(user.getTrust())
                .build();
    }
}
