package com.likelion.besession.domain.contract.exception;

import com.likelion.besession.global.common.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.likelion.besession.domain.contract")
public class ContractExceptionHandler {

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleContractNotFound(ContractNotFoundException ex) {
        log.warn("ContractNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
            .body(BaseResponse.error(String.valueOf(ex.getStatus().value()), ex.getMessage()));
    }

    @ExceptionHandler(ChecklistNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleChecklistNotFound(ChecklistNotFoundException ex) {
        log.warn("ChecklistNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus())
            .body(BaseResponse.error(String.valueOf(ex.getStatus().value()), ex.getMessage()));
    }
}
