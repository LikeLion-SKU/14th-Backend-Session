package com.likelion.besession.domain.user.dto.response;

import com.likelion.besession.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "UserLevelResponse: 사용자 레벨 응답 DTO")
public class UserLevelResponse {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "사용자 레벨", example = "1")
    private int userLevel;

    @Schema(description = "신뢰도 점수", example = "0.0")
    private float trust;

    public static UserLevelResponse from(User user) {
        return UserLevelResponse.builder()
                .userId(user.getId())
                .userLevel(user.getUserLevel())
                .trust(user.getTrust())
                .build();
    }
}
