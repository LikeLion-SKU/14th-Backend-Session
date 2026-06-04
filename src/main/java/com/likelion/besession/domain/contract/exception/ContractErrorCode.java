package com.likelion.besession.domain.contract.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContractErrorCode implements BaseErrorCode {

    CONTRACT_NOT_FOUND("C4001", "존재하지 않는 계약입니다.", HttpStatus.NOT_FOUND),
    CONTRACT_CHECKLIST_NOT_COMPLETE("C4002", "현재 단계의 체크리스트가 모두 완료되지 않았습니다.", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
