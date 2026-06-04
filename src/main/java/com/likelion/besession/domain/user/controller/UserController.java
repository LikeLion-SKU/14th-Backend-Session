package com.likelion.besession.domain.user.controller;

import com.likelion.besession.domain.contractdocs.dto.response.ContractDocsListResponse;
import com.likelion.besession.domain.contractdocs.service.ContractDocsService;
import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.request.UpdateUserRequest;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.dto.response.UpdateUserResponse;
import com.likelion.besession.domain.user.dto.response.UserInfoResponse;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;
    private final ContractDocsService contractDocsService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 이름을 입력받아 사용자를 생성하는 API")
    @PostMapping("/users")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
        SignUpResponse signUpResponse = userService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "회원가입에 성공했습니다.", signUpResponse));
    }

    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 프로필을 조회하는 API")
    @GetMapping("/users")
    public ResponseEntity<BaseResponse<UserInfoResponse>> lookProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserInfoResponse userInfoResponse = userService.lookProfile(userDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "내 정보 조회에 성공했습니다.", userInfoResponse));
    }

    @Operation(summary = "내 프로필 수정", description = "이름과 프로필 사진을 수정하는 API")
    @PutMapping("/users")
    public ResponseEntity<BaseResponse<UpdateUserResponse>> updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UpdateUserRequest request) {
        UpdateUserResponse updateUserResponse = userService.updateProfile(userDetails.getUserId(), request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "프로필 수정에 성공했습니다.", updateUserResponse));
    }

    @Operation(summary = "회원 탈퇴", description = "로그인한 사용자의 계정을 삭제하는 API")
    @DeleteMapping("/users")
    public ResponseEntity<BaseResponse<Void>> deleteUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "회원 탈퇴에 성공했습니다.", null));
    }

    @Operation(summary = "내 계약서 분석 목록 조회", description = "로그인한 사용자의 전체 계약서 분석 목록을 조회하는 API")
    @GetMapping("/users/docs")
    public ResponseEntity<BaseResponse<List<ContractDocsListResponse>>> getMyContractDocs(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<ContractDocsListResponse> response = contractDocsService.myContractDocs(userDetails.getUserId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(200, "계약서 목록 조회에 성공했습니다.", response));
    }
}
