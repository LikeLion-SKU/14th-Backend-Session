package com.likelion.besession.domain.contract.exception;

import com.likelion.besession.global.exception.model.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ContractErrorCode implements BaseErrorCode {

    CONTRACT_NOT_FOUND("CONTRACT4041", "계약서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),           // 존재하지 않거나 본인 소유가 아닌 계약서 접근
    CONTRACT_ACCESS_DENIED("CONTRACT4031", "해당 계약서에 접근 권한이 없습니다.", HttpStatus.FORBIDDEN),  // 타인 계약서 접근 시도
    ANALYSIS_NOT_FOUND("CONTRACT4042", "AI 분석 결과가 없습니다. 먼저 AI 분석을 요청해주세요.", HttpStatus.NOT_FOUND), // 분석 요청 전에 결과 조회 시
    ALREADY_ANALYZED("CONTRACT4091", "이미 AI 분석이 완료된 계약서입니다.", HttpStatus.CONFLICT);       // 중복 분석 요청 시

    private final String code;
    private final String message;
    private final HttpStatus status;
}
