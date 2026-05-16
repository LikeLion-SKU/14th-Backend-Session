package com.example.besession.domain.auth.service;

import com.example.besession.domain.auth.dto.request.LoginRequest;
import com.example.besession.domain.auth.dto.response.LoginResponse;
import com.example.besession.security.CustomUserDetails;
import com.example.besession.security.CustomUserDetailsService;
import com.example.besession.security.JwtProvider;
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

    // 로그인 처리
    public LoginResponse login(LoginRequest request) {
        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}