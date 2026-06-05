package com.example.week09likelion.domain.user.dto.response;

import com.example.week09likelion.domain.user.entity.User;
import com.example.week09likelion.domain.user.entity.UserLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {

    private Long id;
    private String name;
    private UserLevel level;

    /* User 엔티티를 내 정보 응답 DTO로 변환 */
    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .level(user.getLevel())
                .build();
    }
}