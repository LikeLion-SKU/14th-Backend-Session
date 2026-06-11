package com.likelion.besession.domain.user.controller;

import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.dto.response.UserDetailResponse;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.service.UserService;
import com.likelion.besession.global.common.BaseResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 이름을 입력받아 사용자 생성하는 API")
    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest signUpRequest) { // @Valid -> 유효 DTO 검증
        SignUpResponse signUpResponse = userService.signUp(signUpRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BaseResponse.success(201, "회원가입에 성공하였습니다.", signUpResponse));
    }

    @Operation(summary = "내 정보 조회", description = "내 유저 정보를 확인하는 API")
    @GetMapping("/me")
    public ResponseEntity<BaseResponse<UserDetailResponse>> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        UserDetailResponse userDetailResponse = userService.getUserDetails(customUserDetails.getUser().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.success(userDetailResponse));

    }
}
