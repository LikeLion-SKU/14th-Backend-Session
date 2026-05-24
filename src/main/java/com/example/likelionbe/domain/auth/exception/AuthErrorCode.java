package com.example.likelionbe.domain.auth.exception;

import com.example.likelionbe.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    INVALID_EMAIL_OR_PASSWORD("A001", "Email이나 Password가 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("A002", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);


    private final String code;
    private final String message;
    private final HttpStatus status;
}
