package com.likelion.besession.domain.timeline.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TimelineErrorCode implements BaseErrorCode {
    TIMELINE_NOT_FOUND("TIMELINE4041", "타임라인을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_STAGE("TIMELINE4001", "유효하지 않은 단계 값입니다. (BEFORE, DURING, AFTER)", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_ACCESS("TIMELINE4031", "해당 타임라인에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
