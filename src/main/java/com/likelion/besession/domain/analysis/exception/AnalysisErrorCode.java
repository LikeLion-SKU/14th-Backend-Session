package com.likelion.besession.domain.analysis.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AnalysisErrorCode implements BaseErrorCode {
    ANALYSIS_NOT_FOUND("ANALYSIS4041", "분석 결과를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_ACCESS("ANALYSIS4031", "해당 분석 결과에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;
}
