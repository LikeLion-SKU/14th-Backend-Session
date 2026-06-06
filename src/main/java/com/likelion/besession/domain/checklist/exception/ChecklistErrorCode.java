package com.likelion.besession.domain.checklist.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ChecklistErrorCode implements BaseErrorCode {
    CHECKLIST_NOT_FOUND("CHECKLIST4041", "체크리스트 항목을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_STAGE("CHECKLIST4001", "유효하지 않은 단계 값입니다. (BEFORE, DURING, AFTER)", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ACCESS("CHECKLIST4031", "해당 체크리스트에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
