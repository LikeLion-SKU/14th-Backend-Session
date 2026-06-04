package com.likelion.besession.domain.contractdocs.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ContractDocsErrorCode implements BaseErrorCode {

    CONTRACT_DOCS_NOT_FOUND("CD4001","존재하지 않는 계약서입니다.", HttpStatus.NOT_FOUND),
    CONTRACT_DOCS_ALREADY_EXISTS("CD4002", "이미 계약서가 존재합니다.", HttpStatus.CONFLICT);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
