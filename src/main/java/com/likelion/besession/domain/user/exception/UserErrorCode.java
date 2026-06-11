package com.likelion.besession.domain.user.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    USER_DUPLICATED_EMAIL("USER4091", "이미 가입된 이메일입니다.", HttpStatus.CONFLICT),
    USER_NOT_FOUND("USER4041", "유저가 존재하지 않습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
