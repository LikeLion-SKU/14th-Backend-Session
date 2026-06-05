package com.example.week09likelion.domain.user.service;

import com.example.week09likelion.domain.user.dto.request.SignUpRequest;
import com.example.week09likelion.domain.user.dto.response.SignUpResponse;
import com.example.week09likelion.domain.user.dto.response.UserInfoResponse;
import com.example.week09likelion.domain.user.entity.User;
import com.example.week09likelion.domain.user.exception.UserErrorCode;
import com.example.week09likelion.domain.user.repository.UserRepository;
import com.example.week09likelion.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.week09likelion.domain.user.entity.UserLevel;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public SignUpResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(UserErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .level(UserLevel.BEGINNER)
                .build();

        User savedUser = userRepository.save(user);

        return SignUpResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .build();
    }

    // 내 정보 조회
    public UserInfoResponse getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        return UserInfoResponse.from(user);
    }
}
