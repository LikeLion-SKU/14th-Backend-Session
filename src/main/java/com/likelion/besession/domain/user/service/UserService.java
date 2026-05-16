package com.likelion.besession.domain.user.service;

import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.entity.Role;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.repository.UserRepository;
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
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
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
}
