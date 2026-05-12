package com.likelion.besession.domain.auth.service;

import com.likelion.besession.domain.auth.dto.resquest.LoginRequest;
import com.likelion.besession.domain.auth.dto.response.LoginResponse;
import com.likelion.besession.global.security.CustomUserDetails;
import com.likelion.besession.global.security.CustomUserDetailsService;
import com.likelion.besession.global.security.JwtProvider;
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
    log.info("Login request: {}", request);

    CustomUserDetails userDetails;
    try {
      //이메일로 사용자 조회
      userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());
    } catch (Exception e) {
      // UsernameNotFoundException은 AuthenticationException 하위 클래스라서
      // Spring Security 필터가 잡아 403을 반환함 → IllegalArgumentException으로 변환
      throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다");
    }

    log.info("User: {}", userDetails);
    //입력한 비밀번호와 DB에 저장된 암호화 비밀번호 비교
    if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
      throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다");
    }

    //로그인 성공 시 Access Token 발급
    String accessToken = jwtProvider.createAccessToken(userDetails);

    log.info("Access token: {}", accessToken);
    return LoginResponse.builder()
        .accessToken(accessToken)
        .build();
  }

}
