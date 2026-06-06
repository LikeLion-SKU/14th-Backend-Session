package com.likelion.besession.domain.user.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public UserNotFoundException() {
        super("사용자를 찾을 수 없습니다.");
    }

    public HttpStatus getStatus() {
        return status;
    }
}
