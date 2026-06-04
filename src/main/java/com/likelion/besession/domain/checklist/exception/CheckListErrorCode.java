package com.likelion.besession.domain.checklist.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CheckListErrorCode implements BaseErrorCode {

    CHECK_LIST_NOT_FOUND("CL4001", "존재하지 않는 체크리스트입니다.", HttpStatus.NOT_FOUND),
    CHECK_LIST_FORBIDDEN("CL4002", "해당 체크리스트에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
