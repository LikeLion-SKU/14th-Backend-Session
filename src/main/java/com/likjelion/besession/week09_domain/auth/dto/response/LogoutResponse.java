package com.likjelion.besession.week09_domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "로그아웃 응답 DTO")
public class LogoutResponse {

    @Schema(description = "로그아웃 성공 메시지")
    private String message;
}
