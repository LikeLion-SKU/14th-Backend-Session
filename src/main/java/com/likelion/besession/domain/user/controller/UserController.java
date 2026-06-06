package com.likelion.besession.domain.user.controller;

import com.likelion.besession.domain.analysis.dto.response.AnalysisSummaryResponse;
import com.likelion.besession.domain.user.dto.request.UpdatePasswordRequest;
import com.likelion.besession.domain.user.dto.request.UpdateUserRequest;
import com.likelion.besession.domain.user.dto.response.UserResponse;
import com.likelion.besession.domain.user.service.UserService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "내 정보 조회", description = "마이페이지 정보(이름, 레벨, 현재 진행 단계) 조회")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> getMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponse response = userService.getMyInfo(userDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("내 정보 조회에 성공했습니다.", response));
    }

    @Operation(summary = "보관함 조회", description = "저장한 계약서 분석 목록 조회")
    @GetMapping("/me/analyses")
    public ResponseEntity<BaseResponse<List<AnalysisSummaryResponse>>> getMyAnalyses(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<AnalysisSummaryResponse> response = userService.getMyAnalyses(userDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("보관함 조회에 성공했습니다.", response));
    }

    @Operation(summary = "내 정보 수정", description = "이름 등 내 정보 수정")
    @PatchMapping("/me")
    public ResponseEntity<BaseResponse<UserResponse>> updateMyInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateMyInfo(userDetails.getUser().getId(), request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("내 정보 수정에 성공했습니다.", response));
    }

    @Operation(summary = "비밀번호 변경", description = "현재 비밀번호 확인 후 새 비밀번호로 변경")
    @PatchMapping("/me/password")
    public ResponseEntity<BaseResponse<Void>> updatePassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(userDetails.getUser().getId(), request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("비밀번호 변경에 성공했습니다.", null));
    }

    @Operation(summary = "회원 탈퇴", description = "계정 및 연관 데이터 삭제")
    @DeleteMapping("/me")
    public ResponseEntity<BaseResponse<Void>> deleteUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getUser().getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success("회원 탈퇴에 성공했습니다.", null));
    }
}
