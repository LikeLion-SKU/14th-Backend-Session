package com.example.likelionkang.domain.auth.service;


import com.example.likelionkang.domain.auth.Exception.AuthErrorCode;
import com.example.likelionkang.domain.auth.dto.request.LoginRequest;
import com.example.likelionkang.domain.auth.dto.response.LoginResponse;
import com.example.likelionkang.domain.global.exception.CustomException;
import com.example.likelionkang.domain.global.security.CustomUserDetails;
import com.example.likelionkang.domain.global.security.CustomUserDetailsService;
import com.example.likelionkang.domain.global.security.JwtProvider;
import com.example.likelionkang.domain.post.Exception.PostErrorCode;
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

        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.getEmail());

        if(!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new CustomException(AuthErrorCode.AUTH_NOT_FOUND);
        }

        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
                .accesToken(accessToken)
                .build();
    }
}
