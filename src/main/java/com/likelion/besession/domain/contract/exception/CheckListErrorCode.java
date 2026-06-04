package com.likelion.besession.domain.contract.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CheckListErrorCode implements BaseErrorCode {
    CHECKLIST_NOT_FOUND("CHECKLIST4041", "해당 체크리스트 항목을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}