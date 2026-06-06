package com.likelion.besession.domain.schedule.exception;

import org.springframework.http.HttpStatus;

public class ScheduleNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public ScheduleNotFoundException() {
        super("일정을 찾을 수 없습니다.");
    }

    public HttpStatus getStatus() {
        return status;
    }
}
