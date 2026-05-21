package com.likjelion.besession.global.exception;

import com.likjelion.besession.global.common.BaseResponse;
import com.likjelion.besession.global.exception.model.BaseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice //컨트롤러에서 발생하는 예외를 여기서 치리해서 반환하겠다
public class GlobalExceptionHandler {

    // 커스텀 예외
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        log.warn("CustomException 발생: {} - {}", errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatus())
                .body(BaseResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }

    // Validation 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex) {
        String errorMessages =
                ex.getBindingResult().getFieldErrors().stream()
                        .map(e -> String.format("[%s] %s", e.getField(), e.getDefaultMessage()))
                        .collect(Collectors.joining(" / "));
        log.warn("Validation 오류 발생: {}", errorMessages);
        return ResponseEntity.badRequest().body(BaseResponse.error(GlobalErrorCode.INVALID_INPUT_VALUE.getCode(), GlobalErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    // 요청 body JSON 파싱 실패 (잘못된 형식 or 누락)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        log.warn("요청 body 파싱 실패: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(BaseResponse.error(GlobalErrorCode.INVALID_INPUT_VALUE.getCode(), GlobalErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    // 지원하지 않는 HTTP 메서드
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        log.warn("지원하지 않는 HTTP 메서드: {}", ex.getMethod());
        return ResponseEntity.status(GlobalErrorCode.METHOD_NOT_ALLOWED.getStatus())
                .body(BaseResponse.error(GlobalErrorCode.METHOD_NOT_ALLOWED.getCode(), GlobalErrorCode.METHOD_NOT_ALLOWED.getMessage()));
    }

    // 예상치 못한 예외
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception ex) {
        log.error("Server 오류 발생: ", ex);
        return ResponseEntity.status(GlobalErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(BaseResponse.error(GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(), GlobalErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
