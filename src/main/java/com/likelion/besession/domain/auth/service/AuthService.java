package com.likelion.besession.domain.auth.service;

import com.likelion.besession.domain.auth.dto.resquest.LoginRequest;
import com.likelion.besession.domain.auth.dto.response.LoginResponse;
import com.likelion.besession.domain.auth.exception.AuthErrorCode;
import com.likelion.besession.global.exception.CustomException;
import com.likelion.besession.global.security.CustomUserDetails;
import com.likelion.besession.global.security.CustomUserDetailsService;
import com.likelion.besession.global.security.JwtProvider;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

  private final CustomUserDetailsService customUserDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;

  @Transactional
  public LoginResponse login(LoginRequest request) {

    CustomUserDetails userDetails = Optional.ofNullable(customUserDetailsService.loadUserByUsername(request.getEmail()))
        .map(CustomUserDetails.class::cast)
        .orElseThrow(() -> new CustomException(AuthErrorCode.LOGIN_FAILED));


    //입력한 비밀번호와 DB에 저장된 암호화 비밀번호 비교
    if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
      throw new CustomException(AuthErrorCode.LOGIN_FAILED);
    }

    //로그인 성공 시 Access Token 발급
    String accessToken = jwtProvider.createAccessToken(userDetails);

    return LoginResponse.builder()
        .accessToken(accessToken)
        .build();
  }

}
