package com.likelion.besession.domain.user.service;

import com.likelion.besession.domain.analysis.dto.response.AnalysisSummaryResponse;
import com.likelion.besession.domain.analysis.entity.Analysis;
import com.likelion.besession.domain.analysis.repository.AnalysisRepository;
import com.likelion.besession.domain.user.dto.request.UpdatePasswordRequest;
import com.likelion.besession.domain.user.dto.request.UpdateUserRequest;
import com.likelion.besession.domain.user.dto.response.UserResponse;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final AnalysisRepository analysisRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse getMyInfo(Long userId) {
        User user = findUserById(userId);

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userLevel(user.getUserLevel())
                .currentStage(user.getCurrentStage())
                .build();
    }

    public List<AnalysisSummaryResponse> getMyAnalyses(Long userId) {
        List<Analysis> analyses = analysisRepository.findByContractUserIdOrderByCreatedAtDesc(userId);

        return analyses.stream()
                .map(analysis -> AnalysisSummaryResponse.builder()
                        .analysisId(analysis.getId())
                        .imageUrl(analysis.getImageUrl())
                        .createdAt(analysis.getCreatedAt())
                        .build())
                .toList();
    }

    @Transactional
    public UserResponse updateMyInfo(Long userId, UpdateUserRequest request) {
        User user = findUserById(userId);
        user.updateName(request.name());

        return UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .userLevel(user.getUserLevel())
                .currentStage(user.getCurrentStage())
                .build();
    }

    @Transactional
    public void updatePassword(Long userId, UpdatePasswordRequest request) {
        User user = findUserById(userId);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new CustomException(UserErrorCode.INVALID_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(request.newPassword()));
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
    }
}
