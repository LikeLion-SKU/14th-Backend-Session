package com.example.besession.domain.auth.service;

import com.example.besession.domain.auth.dto.request.LoginRequest;
import com.example.besession.domain.auth.dto.response.LoginResponse;
import com.example.besession.domain.auth.exception.AuthErrorCode;
import com.example.besession.global.security.CustomUserDetails;
import com.example.besession.global.security.CustomUserDetailsService;
import com.example.besession.global.security.JwtProvider;
import com.example.besession.global.exception.CustomException;
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
        // email로 사용자 조회
        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());

        // 입력한 비밀번호와 DB에 저장된 암호화 비밀번호 비교
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new CustomException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        // 로그인 성공 시 Access Token 발급
        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}