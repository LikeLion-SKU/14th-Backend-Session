package com.example.week09likelion.domain.auth.exception;

import com.example.week09likelion.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    USER_NOT_FOUND("A001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    LOGIN_FAILED("A002", "이메일 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;
}