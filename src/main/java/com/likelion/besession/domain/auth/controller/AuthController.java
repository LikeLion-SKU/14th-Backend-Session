package com.likelion.besession.domain.auth.controller;

import com.likelion.besession.domain.auth.dto.request.LoginRequest;
import com.likelion.besession.domain.auth.dto.response.LoginResponse;
import com.likelion.besession.domain.auth.service.AuthService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 검증한 뒤 Access Token을 발급하는 API")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(loginResponse));
    }

    @Operation(summary = "로그아웃", description = "사용자가 로그아웃하는 API")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        authService.logout(userDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "로그아웃에 성공했습니다.", null));
    }
}
