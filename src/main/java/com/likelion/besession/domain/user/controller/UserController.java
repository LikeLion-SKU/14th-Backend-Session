package com.likelion.besession.domain.user.controller;

import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.request.UpdateProfileRequest;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.dto.response.UserLevelResponse;
import com.likelion.besession.domain.user.dto.response.UserProfileResponse;
import com.likelion.besession.domain.user.service.UserService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.config.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
                .body(BaseResponse.success(201, "회원가입에 성공했습니다.", signUpResponse));
    }

    @Operation(summary = "내 프로필 조회", description = "로그인한 사용자의 프로필 정보를 조회하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserProfileResponse>> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserProfileResponse response = userService.getProfile(userDetails.getUser().getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "내 프로필 수정", description = "로그인한 사용자의 이름, 프로필 이미지를 수정하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/me")
    public ResponseEntity<BaseResponse<UserProfileResponse>> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateProfileRequest request) {
        UserProfileResponse response = userService.updateProfile(userDetails.getUser().getId(), request);
        return ResponseEntity.ok(BaseResponse.success("프로필이 수정되었습니다.", response));
    }

    @Operation(summary = "내 레벨 조회", description = "로그인한 사용자의 레벨과 신뢰도를 조회하는 API")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me/level")
    public ResponseEntity<BaseResponse<UserLevelResponse>> getLevel(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserLevelResponse response = userService.getLevel(userDetails.getUser().getId());
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}