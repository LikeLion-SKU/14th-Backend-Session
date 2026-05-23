package com.wacaw.besession.domain.user.exception;

import com.wacaw.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StudentErrorCode implements BaseErrorCode {

  DUPLICATE_EMAIL("STUDENT4092", "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT);

  private final String code;
  private final String message;
  private final HttpStatus status;

}
