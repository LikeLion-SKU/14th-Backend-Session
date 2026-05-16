package com.example.likelionbe.domain.auth.controller;

import com.example.likelionbe.domain.auth.dto.request.LoginRequest;
import com.example.likelionbe.domain.auth.dto.response.LoginResponse;
import com.example.likelionbe.domain.auth.service.AuthService;
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
@Tag(name = "Auth", description = "인증 관려 API")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "로그인", description = "이메일, 비밀번호를 입력받아 로그인하는 API")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
