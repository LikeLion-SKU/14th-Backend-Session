package com.likjelion.besession.domain.auth.service;

import com.likjelion.besession.domain.auth.dto.request.LoginRequest;
import com.likjelion.besession.domain.auth.dto.response.LoginResponse;
import com.likjelion.besession.domain.auth.exception.AuthErrorCode;
import com.likjelion.besession.global.exception.CustomException;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.global.sequrity.CustomUserDetailsService;
import com.likjelion.besession.global.sequrity.JwtProvider;
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

    public LoginResponse login(LoginRequest request) {
        //email로 사용자 조회
        CustomUserDetails userDetails;
        try {
            userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());
        } catch (UsernameNotFoundException e) {
            throw new CustomException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        //입력한 비밀번호와 db 저장된 암호를 비교
        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new CustomException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
