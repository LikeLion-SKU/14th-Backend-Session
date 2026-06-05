package com.likjelion.besession.domain.user_v1.service;

import com.likjelion.besession.domain.user_v1.dto.request.SignUpRequest;
import com.likjelion.besession.domain.user_v1.dto.response.SignUpResponse;
import com.likjelion.besession.domain.user_v1.entity.Role;
import com.likjelion.besession.domain.user_v1.entity.UserV1;
import com.likjelion.besession.domain.user_v1.exception.UserErrorCodeV1;
import com.likjelion.besession.domain.user_v1.repository.UserRepositoryV1;
import com.likjelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceV1 {

    private final UserRepositoryV1 userRepositoryV1;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        if(userRepositoryV1.existsByEmail(request.getEmail())) {
            throw new CustomException(UserErrorCodeV1.DUPLICATE_EMAIL);
        }

        UserV1 userV1 = UserV1.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(Role.USER)
                .build();

        UserV1 savedUserV1 = userRepositoryV1.save(userV1);

        return SignUpResponse.builder().
                userId(savedUserV1.getId())
                .email(savedUserV1.getEmail())
                .name(savedUserV1.getName())
                .build();
    }
}
