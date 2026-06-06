package com.likelion.besession.domain.auth.controller;

import com.likelion.besession.domain.auth.dto.request.LoginRequest;
import com.likelion.besession.domain.auth.dto.request.SignUpRequest;
import com.likelion.besession.domain.auth.dto.response.LoginResponse;
import com.likelion.besession.domain.auth.service.AuthService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "회원가입", description = "이메일·비밀번호·이름을 입력받아 회원 등록")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<Void>> signUp(@Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success("회원가입에 성공했습니다.", null));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 검증한 뒤 Access Token을 발급하는 API")
    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("로그인에 성공했습니다.", loginResponse));
    }

    @Operation(summary = "로그아웃", description = "현재 토큰을 무효화 처리")
    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Void>> logout() {
        // TODO: 토큰 블랙리스트 등록 로직 구현
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("로그아웃에 성공했습니다.", null));
    }
}
