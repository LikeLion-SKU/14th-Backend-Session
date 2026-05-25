package com.example.besession.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "LoginResponse: 로그인 응답 DTO")
public class LoginResponse {

    @Schema(description = "발급된 Access Token")
    private String accessToken;

}