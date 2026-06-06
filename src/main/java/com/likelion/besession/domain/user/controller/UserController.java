package com.likelion.besession.domain.user.controller;

import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.response.MyPageResponse;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.service.UserService;
import com.likelion.besession.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "사용자 관련 API")
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입", description = "이메일, 비밀번호, 이름을 입력받아 사용자를 생성하는 API")
  @PostMapping("/signup")
  public ResponseEntity<BaseResponse<SignUpResponse>> signUp(@Valid @RequestBody SignUpRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(BaseResponse.success(userService.signUp(request)));
  }

  @Operation(summary = "마이페이지 조회", description = "사용자 ID로 이름과 레벨을 조회")
  @GetMapping("/{id}/my")
  public ResponseEntity<BaseResponse<MyPageResponse>> getMyPage(@PathVariable Long id) {
    return ResponseEntity.ok(BaseResponse.success(userService.getMyPage(id)));
  }
}
