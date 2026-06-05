package com.likjelion.besession.week09_domain.user.controller;

import com.likjelion.besession.global.common.BaseResponse;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.week09_domain.user.dto.request.UpdateUserRequest;
import com.likjelion.besession.week09_domain.user.dto.response.UserProfileResponse;
import com.likjelion.besession.week09_domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "사용자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "프로필 및 통합 조회", description = "현재 로그인한 사용자의 프로필 정보를 조회합니다. 마이페이지에서 이름, 이메일, 레벨, 프로필 이미지 등을 표시할 때 사용합니다.")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserProfileResponse>> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(BaseResponse.success(200, "프로필 조회 성공", userService.getMyProfile(userDetails)));
    }

    @Operation(summary = "프로필 수정", description = "현재 로그인한 사용자의 이름, 성별, 프로필 이미지를 수정합니다.")
    @PatchMapping("/me")
    public ResponseEntity<BaseResponse<UserProfileResponse>> updateMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(BaseResponse.success(200, "프로필 수정 성공", userService.updateMyProfile(userDetails, request)));
    }
}
