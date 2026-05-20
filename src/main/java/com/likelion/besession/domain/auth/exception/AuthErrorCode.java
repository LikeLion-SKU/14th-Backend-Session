package com.likelion.besession.domain.auth.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode; // 패키지 경로는 본인 프로젝트에 맞게 확인
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    USER_NOT_FOUND("AUTH4041", "존재하지 않는 이메일입니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("AUTH4011", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;
}