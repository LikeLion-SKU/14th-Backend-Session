package com.likelion.besession.domain.user.service;

import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.request.UpdateUserRequest;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.dto.response.UpdateUserResponse;
import com.likelion.besession.domain.user.dto.response.UserInfoResponse;
import com.likelion.besession.domain.user.entity.Role;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        // 중복 가입 예외 처리
        if(userRepository.existsByEmail(request.getEmail())) {
            log.warn("[UserService] 중복된 이메일입니다: email= {}", request.getEmail());
            throw new CustomException(UserErrorCode.USER_DUPLICATE);
        }

        // 사용자 객체 생성
        User user =
                User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .name(request.getName())
                        .image(request.getImage())
                        .build();

        // 사용자 DB에 저장
        User savedUser = userRepository.save(user);

        log.info("[UserService] 회원가입 완료: email= {}", user.getEmail());

        // 응답 세팅
        return SignUpResponse.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .image(savedUser.getImage())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    // 내 정보 조회
    public UserInfoResponse lookProfile(Long userId) {

        // 사용자가 존재하는지 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        log.info("[UserService] 내 정보 조회 성공: userId= {}", user.getUserId());

        return UserInfoResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .image(user.getImage())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // 프로필 수정
    @Transactional
    public UpdateUserResponse updateProfile(Long userId, UpdateUserRequest request) {

        // 사용자가 존재하는지 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        user.updateProfile(request.getName(), request.getImage());

        log.info("[UserService] 프로필 수정 완료: userId: {}", user.getUserId());

        return UpdateUserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .image(user.getImage())
                .updatedAt(user.getModifiedAt())
                .build();
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(Long userId) {

        // 사용자가 존재하는지 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
        log.info("[UserService] 사용자 삭제 성공: userId= {}", user.getUserId());
    }
}
