package com.likelion.besession.domain.user.dto.response;

import com.likelion.besession.domain.user.entity.UserLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponse {

    @Schema(description = "사용자 이름", example = "김철수")
    private String name;

    @Schema(description = "사용자 레벨", example = "초급")
    private UserLevel level;
}
