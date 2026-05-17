package com.likjelion.besession.domain.auth.service;

import com.likjelion.besession.domain.auth.dto.request.LoginRequest;
import com.likjelion.besession.domain.auth.dto.response.LoginResponse;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.global.sequrity.CustomUserDetailsService;
import com.likjelion.besession.global.sequrity.JwtProvider;
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
        //email로 사용자 조회
        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());

        //입력한 비밀번호와 db 저장된 암호를 비교
        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가올바르지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
