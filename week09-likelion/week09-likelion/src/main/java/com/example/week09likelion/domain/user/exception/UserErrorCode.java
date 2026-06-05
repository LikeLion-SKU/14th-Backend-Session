package com.example.week09likelion.domain.user.exception;

import com.example.week09likelion.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    DUPLICATE_EMAIL("U001", "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("U002", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_USER("U003", "로그인이 필요한 요청입니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;
}