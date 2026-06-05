package com.likjelion.besession.week09_domain.user.service;

import com.likjelion.besession.global.exception.CustomException;
import com.likjelion.besession.global.exception.GlobalErrorCode;
import com.likjelion.besession.global.sequrity.CustomUserDetails;
import com.likjelion.besession.week09_domain.user.dto.request.UpdateUserRequest;
import com.likjelion.besession.week09_domain.user.dto.response.UserProfileResponse;
import com.likjelion.besession.week09_domain.user.entity.User;
import com.likjelion.besession.week09_domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserProfileResponse getMyProfile(CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return toResponse(user);
    }

    @Transactional
    public UserProfileResponse updateMyProfile(CustomUserDetails userDetails, UpdateUserRequest request) {
        User user = userDetails.getUser();
        userRepository.updateProfile(user.getId(), request.getName(), request.getGender(), request.getImg());
        User updated = userRepository.findById(user.getId())
                .orElseThrow(() -> new CustomException(GlobalErrorCode.RESOURCE_NOT_FOUND));
        return toResponse(updated);
    }

    private UserProfileResponse toResponse(User user) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .gender(user.getGender())
                .email(user.getEmail())
                .level(user.getLevel())
                .img(user.getImg())
                .build();
    }
}
