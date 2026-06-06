package com.likelion.besession.domain.report.exception;

import com.likelion.besession.global.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.likelion.besession.domain.report")
public class ReportExceptionHandler {

    @ExceptionHandler(AiReportNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleAiReportNotFound(AiReportNotFoundException ex) {
        log.warn("AiReportNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
            .body(BaseResponse.error(String.valueOf(ex.getStatus().value()), ex.getMessage()));
    }
}
