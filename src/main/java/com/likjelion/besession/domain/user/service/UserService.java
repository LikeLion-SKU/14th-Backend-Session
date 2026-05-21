package com.likjelion.besession.domain.user.service;

import com.likjelion.besession.domain.user.dto.request.SignUpRequest;
import com.likjelion.besession.domain.user.dto.response.SignUpResponse;
import com.likjelion.besession.domain.user.entity.Role;
import com.likjelion.besession.domain.user.entity.User;
import com.likjelion.besession.domain.user.exception.UserErrorCode;
import com.likjelion.besession.domain.user.repository.UserRepository;
import com.likjelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(UserErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);

        return SignUpResponse.builder().
                userId(savedUser.getId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .build();
    }
}
