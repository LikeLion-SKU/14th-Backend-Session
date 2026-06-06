package com.likelion.besession.domain.contract.exception;

import org.springframework.http.HttpStatus;

public class ChecklistNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public ChecklistNotFoundException() {
        super("체크리스트를 찾을 수 없습니다.");
    }

    public HttpStatus getStatus() {
        return status;
    }
}
