package com.likjelion.besession.week09_domain.auth.exception;

import com.likjelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    DUPLICATE_EMAIL("AUTH409", "이미 가입된 이메일입니다.", HttpStatus.CONFLICT),
    NOT_FOUND_EMAIL("AUTH404", "해당 이메일을 찾을 수 없습니다." , HttpStatus.NOT_FOUND),
    OUTMATCH_PASSWORD("AUTH401", "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    NOT_FOUNT_REFRESH_TOKEN("AUTH404_2", "리프레쉬 토큰을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_REFRESH_TOKEN("AUTH401_2", "유효하지 않거나 위변조된 리프레시 토큰입니다.", HttpStatus.UNAUTHORIZED);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
