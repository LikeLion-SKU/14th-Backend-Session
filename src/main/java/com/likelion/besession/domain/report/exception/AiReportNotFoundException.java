package com.likelion.besession.domain.report.exception;

import org.springframework.http.HttpStatus;

public class AiReportNotFoundException extends RuntimeException {

    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public AiReportNotFoundException() {
        super("AI 분석 결과를 찾을 수 없습니다.");
    }

    public HttpStatus getStatus() {
        return status;
    }
}
