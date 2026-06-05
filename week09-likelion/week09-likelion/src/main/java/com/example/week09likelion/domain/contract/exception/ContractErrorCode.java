package com.example.week09likelion.domain.contract.exception;

import com.example.week09likelion.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ContractErrorCode implements BaseErrorCode {

    CONTRACT_NOT_FOUND("C001", "계약 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_CONTRACT("C002", "이미 등록된 계약 정보가 있습니다.", HttpStatus.CONFLICT),
    CONTRACT_ACCESS_DENIED("C003", "해당 계약에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}