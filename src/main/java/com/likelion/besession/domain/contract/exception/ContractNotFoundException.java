package com.likelion.besession.domain.contract.exception;

import org.springframework.http.HttpStatus;

public class ContractNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public ContractNotFoundException() {
        super("계약을 찾을 수 없습니다.");
    }

    public HttpStatus getStatus() {
        return status;
    }
}
