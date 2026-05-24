package com.example.likelionbe.domain.user.exception;

import com.example.likelionbe.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {

    EMAIL_CONFLICT("U001", "이미 가입된 이메일입니다", HttpStatus.CONFLICT);


    private final String code;
    private final String message;
    private final HttpStatus status;
}