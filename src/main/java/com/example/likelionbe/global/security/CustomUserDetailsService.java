package com.example.likelionbe.global.security;

import com.example.likelionbe.domain.auth.exception.AuthErrorCode;
import com.example.likelionbe.domain.user.entity.User;
import com.example.likelionbe.domain.user.repository.UserRepository;
import com.example.likelionbe.global.exception.CustomException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 로그인 시 email로 사용자 조회
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));
        return new CustomUserDetails(user);
    }

    // JWT 인증 필터에서 userId로 사용자 조회
    public CustomUserDetails loadUserById(Long userId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new CustomException(AuthErrorCode.USER_NOT_FOUND));
        return new CustomUserDetails(user);
    }
}