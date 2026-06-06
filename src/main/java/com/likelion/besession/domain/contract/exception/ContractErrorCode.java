package com.likelion.besession.domain.contract.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ContractErrorCode implements BaseErrorCode {
    CONTRACT_NOT_FOUND("CONTRACT4041", "계약을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CONTRACT_ALREADY_EXISTS("CONTRACT4091", "이미 계약이 존재합니다.", HttpStatus.CONFLICT),
    INVALID_STATUS("CONTRACT4001", "유효하지 않은 계약 상태입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ACCESS("CONTRACT4031", "해당 계약에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
