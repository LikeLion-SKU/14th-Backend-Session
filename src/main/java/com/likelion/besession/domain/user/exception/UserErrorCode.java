package com.likelion.besession.domain.user.exception;


import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    DUPLICATE_EMAIL("USER4091", "이미 가입된 이메일입니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;

}
