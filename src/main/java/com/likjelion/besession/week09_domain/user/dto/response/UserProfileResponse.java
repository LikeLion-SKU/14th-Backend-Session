package com.likjelion.besession.week09_domain.user.dto.response;

import com.likjelion.besession.week09_domain.user.entity.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {
    private Long userId;
    private String name;
    private Gender gender;
    private String email;
    private int level;
    private String img;
}
