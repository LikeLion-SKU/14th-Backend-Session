package com.likelion.besession.domain.user.service;

import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.dto.response.UserDetailResponse;
import com.likelion.besession.domain.user.entity.Role;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException(UserErrorCode.USER_DUPLICATED_EMAIL);
        }

        User user =
                User.builder()
                        .email(signUpRequest.getEmail())
                        .password(passwordEncoder.encode(signUpRequest.getPassword()))
                        .name(signUpRequest.getName())
                        .role(Role.USER)
                        .build();

        User savedUser = userRepository.save(user);

        return SignUpResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .build();
    }

    // 유저 조회
    public UserDetailResponse getUserDetails(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        return toUserDetailResponse(user);
    }

    // 유저 상세 DTO 변환
    private UserDetailResponse toUserDetailResponse(User user) {
        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .level(user.getLevel())
                .build();
    }
}
