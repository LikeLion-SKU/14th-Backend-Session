package com.likelion.besession.domain.schedule.exception;

import com.likelion.besession.global.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.likelion.besession.domain.schedule")
public class ScheduleExceptionHandler {

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleScheduleNotFound(ScheduleNotFoundException ex) {
        log.warn("ScheduleNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
            .body(BaseResponse.error(String.valueOf(ex.getStatus().value()), ex.getMessage()));
    }
}
