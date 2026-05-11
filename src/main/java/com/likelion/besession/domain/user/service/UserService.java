package com.likelion.besession.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.entity.Role;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SignUpResponse signUp(SignUpRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("이미 가입된 이메일입니다.");
    }

    User user =
        User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
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