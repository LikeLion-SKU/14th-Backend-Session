package com.wacaw.besession.domain.auth.exception;

import com.wacaw.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

  LOGIN_FAILED("AUTH4011", "아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);

  private final String code;
  private final String message;
  private final HttpStatus status;
}
