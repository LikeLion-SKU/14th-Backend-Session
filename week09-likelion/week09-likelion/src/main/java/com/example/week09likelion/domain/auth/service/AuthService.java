package com.example.week09likelion.domain.auth.service;

import com.example.week09likelion.domain.auth.dto.request.LoginRequest;
import com.example.week09likelion.domain.auth.dto.response.LoginResponse;
import com.example.week09likelion.domain.auth.exception.AuthErrorCode;
import com.example.week09likelion.global.exception.CustomException;
import com.example.week09likelion.security.CustomUserDetails;
import com.example.week09likelion.security.CustomUserDetailsService;
import com.example.week09likelion.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        CustomUserDetails userDetails;

        try {
            userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new CustomException(AuthErrorCode.LOGIN_FAILED);
        }

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new CustomException(AuthErrorCode.LOGIN_FAILED);
        }

        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}