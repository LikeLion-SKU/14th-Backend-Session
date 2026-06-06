package com.likelion.besession.domain.checklist.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChecklistErrorCode implements BaseErrorCode {

    CHECKLIST_NOT_FOUND("CHECKLIST4041", "체크리스트를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus status;
}