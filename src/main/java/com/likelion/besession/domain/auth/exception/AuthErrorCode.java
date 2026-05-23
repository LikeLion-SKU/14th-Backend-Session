package com.likelion.besession.domain.auth.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
  LOGIN_FAILED("AUTH4011", "이메일 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
  TOKEN_EXPIRED("AUTH4013", "만료된 인증 토큰입니다.", HttpStatus.UNAUTHORIZED),
  UNAUTHORIZED("AUTH4012", "인증 정보가 누락되었거나 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
