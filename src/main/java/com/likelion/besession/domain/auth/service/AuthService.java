package com.likelion.besession.domain.auth.service;

import com.likelion.besession.domain.auth.dto.request.LoginRequest;
import com.likelion.besession.domain.auth.dto.request.SignUpRequest;
import com.likelion.besession.domain.auth.dto.response.LoginResponse;
import com.likelion.besession.domain.auth.exception.AuthErrorCode;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(UserErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .build();

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        CustomUserDetails userDetails =
                (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.email());

        if (!passwordEncoder.matches(request.password(), userDetails.getPassword())) {
            throw new CustomException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        String accessToken = jwtProvider.createAccessToken(userDetails);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .build();
    }
}
