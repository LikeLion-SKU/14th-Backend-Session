package com.likjelion.besession.week09_domain.auth.controller;

import com.likjelion.besession.global.common.BaseResponse;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.week09_domain.auth.dto.request.LoginRequest;
import com.likjelion.besession.week09_domain.auth.dto.request.SignUpRequest;
import com.likjelion.besession.week09_domain.auth.dto.request.TokenRequest;
import com.likjelion.besession.week09_domain.auth.dto.response.LoginResponse;
import com.likjelion.besession.week09_domain.auth.dto.response.LogoutResponse;
import com.likjelion.besession.week09_domain.auth.dto.response.SignUpResponse;
import com.likjelion.besession.week09_domain.auth.dto.response.TokenResponse;
import com.likjelion.besession.week09_domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "사용자의 이메일, 비밀번호, 이름 등의 정보를 입력받아 회원가입을 수행합니다.")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<SignUpResponse>> signup(
            @Valid @RequestBody SignUpRequest signUpRequest)
    {
        SignUpResponse signUpResponse = authService.signup(signUpRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "회원가입 성공", signUpResponse));
    }

    @Operation(summary = "로그인", description = "사용자의 이메일과 비밀번호를 검증하고 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest loginRequest)
    {
        LoginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "로그인 성공", loginResponse));
    }

    @Operation(summary = "로그아웃", description = "현재 사용자의 리프레시 토큰을 만료 또는 삭제하여 로그아웃을 수행합니다.")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<LogoutResponse>> logout(
            // 로그인된 user를 sequrity가 관리하는 userDetail로 받아옴(@AuthenticationPrincipal)
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            )
    {
        LogoutResponse logoutResponse = authService.logout(customUserDetails);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "로그아웃 성공", logoutResponse));
    }

    @Operation(summary = "토큰 갱신", description = "유효한 리프레시 토큰을 이용해 새로운 액세스 토큰을 발급합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<BaseResponse<TokenResponse>> refresh(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody TokenRequest tokenRequest
            )
    {
        TokenResponse tokenResponse = authService.refresh(customUserDetails, tokenRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "토큰 갱신 성공", tokenResponse));
    }


}
