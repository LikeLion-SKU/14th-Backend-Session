package com.likelion.besession.domain.contract.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ContractErrorCode implements BaseErrorCode {
    USER_NOT_FOUND("CONTRACT4041", "해당 사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CONTRACT_NOT_FOUND("CONTRACT4042", "해당 계약 일정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CONTRACT_ALREADY_EXISTS("CONTRACT4091", "사용자는 이미 진행 중인 계약 일정을 가지고 있습니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}