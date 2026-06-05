package com.likjelion.besession.week09_domain.user.dto.request;

import com.likjelion.besession.week09_domain.user.entity.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserRequest {
    private String name;
    private Gender gender;
    private String img;
}
