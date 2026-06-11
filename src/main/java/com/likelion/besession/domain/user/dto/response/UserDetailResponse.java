package com.likelion.besession.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "유저 정보 조회용 DTO")
public class UserDetailResponse {

    private Long id;

    private String name;

    private String email;

    private Long level;
}
