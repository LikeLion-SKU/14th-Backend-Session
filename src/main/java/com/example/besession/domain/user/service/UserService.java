package com.example.besession.domain.user.service;

import com.example.besession.domain.user.dto.request.SignUpRequest;
import com.example.besession.domain.user.dto.response.SignUpResponse;
import com.example.besession.domain.user.entity.Role;
import com.example.besession.domain.user.entity.User;
import com.example.besession.domain.user.repository.UserRepository;
import com.example.besession.domain.user.exception.UserErrorCode;
import com.example.besession.global.exception.CustomException;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(SignUpRequest request) {

        // 이메일 중복 검사
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(UserErrorCode.DUPLICATE_EMAIL);
        }

        // 사용자 생성
        User user =
                User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(request.getName())
                        .role(Role.USER)
                        .build();

        // 저장
        User savedUser = userRepository.save(user);

        // 응답 반환
        return SignUpResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .build();
    }
}