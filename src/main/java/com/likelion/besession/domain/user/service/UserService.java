package com.likelion.besession.domain.user.service;

import com.likelion.besession.domain.user.dto.request.SignUpRequest;
import com.likelion.besession.domain.user.dto.response.MyPageResponse;
import com.likelion.besession.domain.user.dto.response.SignUpResponse;
import com.likelion.besession.domain.user.entity.Role;
import com.likelion.besession.domain.user.entity.User;
import com.likelion.besession.domain.user.exception.UserErrorCode;
import com.likelion.besession.domain.user.exception.UserNotFoundException;
import com.likelion.besession.domain.user.repository.UserRepository;
import com.likelion.besession.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public SignUpResponse signUp(SignUpRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(UserErrorCode.DUPLICATE_USER);
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

  @Transactional(readOnly = true)
  public MyPageResponse getMyPage(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(UserNotFoundException::new);

    return MyPageResponse.builder()
        .name(user.getName())
        .level(user.getLevel())
        .build();
  }

}
