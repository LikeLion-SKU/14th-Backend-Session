package com.likelion.besession.domain.auth.service;

import com.likelion.besession.domain.auth.dto.request.LoginRequest;
import com.likelion.besession.domain.auth.dto.response.LoginResponse;
import com.likelion.besession.domain.auth.exception.AuthErrorCode;
import com.likelion.besession.global.exception.CustomException;
import com.likelion.besession.global.security.CustomUserDetails;
import com.likelion.besession.global.security.CustomUserDetailsService;
import com.likelion.besession.global.security.JwtProvider;
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

    public LoginResponse login(LoginRequest request){
        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())){
            throw new CustomException(AuthErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
             .accessToken(accessToken)
             .build();


    }
}
