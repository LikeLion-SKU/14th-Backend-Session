package com.likjelion.besession.domain.auth.controller;


import com.likjelion.besession.domain.auth.dto.request.LoginRequest;
import com.likjelion.besession.domain.auth.dto.response.LoginResponse;
import com.likjelion.besession.domain.auth.service.AuthServiceV1;
import com.likjelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthControllerV1 {

    private final AuthServiceV1 authService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 검증한 뒤 Access Token을 발급하는 API")
    @PostMapping("/loginV1")
    public ResponseEntity<BaseResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = authService.login(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("로그인에 성공했습니다.", loginResponse));
    }
}