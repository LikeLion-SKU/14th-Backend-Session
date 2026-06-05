package com.example.week09likelion.domain.schedule.exception;

import com.example.week09likelion.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ScheduleErrorCode implements BaseErrorCode {

    SCHEDULE_NOT_FOUND("S001", "계약 일정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    SCHEDULE_ACCESS_DENIED("S002", "해당 계약 일정에 접근할 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}