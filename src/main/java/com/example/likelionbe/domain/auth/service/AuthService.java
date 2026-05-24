package com.example.likelionbe.domain.auth.service;

import com.example.likelionbe.domain.auth.dto.request.LoginRequest;
import com.example.likelionbe.domain.auth.dto.response.LoginResponse;
import com.example.likelionbe.domain.auth.exception.AuthErrorCode;
import com.example.likelionbe.domain.user.repository.UserRepository;
import com.example.likelionbe.global.exception.CustomException;
import com.example.likelionbe.global.security.CustomUserDetails;
import com.example.likelionbe.global.security.CustomUserDetailsService;
import com.example.likelionbe.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request){
        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.email());

        if(!passwordEncoder.matches(request.password(), userDetails.getPassword())){
            throw new CustomException(AuthErrorCode.INVALID_EMAIL_OR_PASSWORD);
        }

        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }

}
