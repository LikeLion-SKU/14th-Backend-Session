package com.likjelion.besession.week09_domain.auth.dto.response;

import com.likjelion.besession.week09_domain.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Schema(description = "회원가입 응답 DTO")
public class SignUpResponse {

    @Schema(description = "생성된 사용자 Id")
    private Long userId;

    @Schema(description = "사용자 이름")
    private String name;

    @NotNull(message = "성별은 필수입니다.")
    private Gender gender;

    @Schema(description = "사용자 이메일")
    private String email;

}
