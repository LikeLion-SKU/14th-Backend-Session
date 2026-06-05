package com.likjelion.besession.week09_domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "토큰 재발급용 요청 DTO")
public class TokenRequest {

    @NotBlank(message = "리프레쉬 토큰은 재발급에서 필수입니다.")
    @Schema(description = "재발급용 refresh_token")
    private String refreshToken;
}
