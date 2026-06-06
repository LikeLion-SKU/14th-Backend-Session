package com.likelion.besession.domain.user.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND("USER4041", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_EMAIL("USER4091", "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    INVALID_PASSWORD("USER4001", "현재 비밀번호가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
