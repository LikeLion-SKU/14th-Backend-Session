package com.example.likelionkang.domain.auth.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "LoginResponse: 로그인 응답 DTO")
public class LoginResponse {
    @Schema(description = "Access Token")
    private String accesToken;
}
