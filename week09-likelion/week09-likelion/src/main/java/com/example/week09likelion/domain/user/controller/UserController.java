package com.example.week09likelion.domain.user.controller;

import com.example.week09likelion.domain.user.dto.request.SignUpRequest;
import com.example.week09likelion.domain.user.dto.response.SignUpResponse;
import com.example.week09likelion.domain.user.dto.response.UserInfoResponse;
import com.example.week09likelion.domain.user.exception.UserErrorCode;
import com.example.week09likelion.domain.user.service.UserService;
import com.example.week09likelion.global.common.BaseResponse;
import com.example.week09likelion.global.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.week09likelion.security.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 이름을 입력받아 사용자를 생성하는 API")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse signUpResponse = userService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "계정 생성에 성공했습니다.", signUpResponse));
    }

    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 이름과 레벨을 조회하는 API")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserInfoResponse>> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        if (customUserDetails == null) {
            throw new CustomException(UserErrorCode.UNAUTHORIZED_USER);
        }

        UserInfoResponse userInfoResponse = userService.getMyInfo(customUserDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "내 정보 조회 성공", userInfoResponse));
    }
}