package com.example.likelionbe.domain.user.service;

import com.example.likelionbe.domain.user.dto.request.SignUpRequest;
import com.example.likelionbe.domain.user.dto.response.SignUpResponse;
import com.example.likelionbe.domain.user.entity.User;
import com.example.likelionbe.domain.user.exception.UserErrorCode;
import com.example.likelionbe.domain.user.repository.UserRepository;
import com.example.likelionbe.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new CustomException(UserErrorCode.EMAIL_CONFLICT);
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);
        return SignUpResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }


}
