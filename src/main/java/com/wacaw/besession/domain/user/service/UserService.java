package com.wacaw.besession.domain.user.service;

import com.wacaw.besession.domain.user.dto.request.SignUpRequest;
import com.wacaw.besession.domain.user.dto.response.SignUpResponse;
import com.wacaw.besession.domain.user.entity.Role;
import com.wacaw.besession.domain.user.entity.User;
import com.wacaw.besession.domain.user.exception.StudentErrorCode;
import com.wacaw.besession.domain.user.repository.UserRespository;
import com.wacaw.besession.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserRespository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SignUpResponse signUp(SignUpRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new CustomException(StudentErrorCode.DUPLICATE_EMAIL);
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
