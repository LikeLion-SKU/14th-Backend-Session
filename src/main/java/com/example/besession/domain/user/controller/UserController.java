package com.example.besession.domain.user.controller;

import com.example.besession.domain.user.dto.request.SignUpRequest;
import com.example.besession.domain.user.dto.response.SignUpResponse;
import com.example.besession.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "회원가입",
            description = "이메일, 비밀번호, 이름을 입력받아 사용자를 생성하는 API"
    )
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {

        SignUpResponse signUpResponse =
                userService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(signUpResponse);
    }
}