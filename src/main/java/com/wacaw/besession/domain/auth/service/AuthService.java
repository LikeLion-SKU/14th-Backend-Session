package com.wacaw.besession.domain.auth.service;

import com.wacaw.besession.domain.auth.dto.request.LoginRequest;
import com.wacaw.besession.domain.auth.dto.response.LoginResponse;
import com.wacaw.besession.domain.auth.exception.AuthErrorCode;
import com.wacaw.besession.global.exception.CustomException;
import com.wacaw.besession.global.security.CustomUserDetails;
import com.wacaw.besession.global.security.CustomUserDetailsService;
import com.wacaw.besession.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

  private final CustomUserDetailsService customUserDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  public LoginResponse login(LoginRequest request) {
    // 이메일로 사용자 조회
    CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(
        request.getEmail());
    // 입력한 비밀번호와 DB에 저장된 암호화 비밀번호 비교
    if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
      throw new CustomException(AuthErrorCode.LOGIN_FAILED);
    }
    // 로그인 성공 시 Access Token 발급
    String accessToken = jwtProvider.createAccessToken(userDetails);

    return LoginResponse.builder()
        .accessToken(accessToken)
        .build();
  }

}
